package com.leo.reportes.usecase;

import com.leo.reportes.domain.gateway.StudentGateway;
import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.domain.models.User;
import com.leo.reportes.domain.port.CourseRepository;
import com.leo.reportes.domain.port.GradesRepository;
import com.leo.reportes.domain.port.UserRepository;
import com.leo.reportes.infrastructure.exceptions.UserNotFoundException;
import com.leo.reportes.infrastructure.models.UserEntity;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StudentUsecase implements StudentGateway {

    private final CourseRepository courseRepository;
    private final GradesRepository gradesRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, byte[]> redisTemplate;

    private static final String USERNOTFOUND = "Usuario no encontrado";
    private static final Logger logger = LoggerFactory.getLogger(StudentUsecase.class);
    @Override
    @Transactional
    public byte[] generateReport(long id) {
        JasperPrint jasperPrint;
        var cache = redisTemplate.opsForValue().get(String.valueOf(id));
        if (Optional.ofNullable(cache).isPresent()) {
            logger.info("Reporte generado desde cache");
            return cache;
        } else {
            var userEntity = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException(USERNOTFOUND)
            );
            if (!gradesRepository.existsGrades(userEntity)) {
                Map<String, Double> gradesMap = new HashMap<>();
                courseRepository.getAllCourses().forEach(course -> {
                            double randomScore = Math.random() * 10;
                            DecimalFormat format = new DecimalFormat("#.#");
                            gradesMap.put(course.getName(), Double.parseDouble(format.format(randomScore).replace(",", ".")));
                        }
                );
                gradesRepository.saveGrades(id, gradesMap);
            }
            jasperPrint = gradesRepository.generateReport(id);



            try {
                logger.info("Reporte generado desde la base de datos");
                var result = JasperExportManager.exportReportToPdf(jasperPrint);
                redisTemplate.opsForValue().set(String.valueOf(id),result);
                return result;

            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public User generateResult(long id) {
        List<Grade> gradeUserList = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(USERNOTFOUND)
        );
        gradesRepository.findGradesByUserId(userEntity).forEach(grades -> {
            Grade grade = Grade.builder()
                    .courseName(grades.getCourseName())
                    .grade(grades.getGrade())
                    .build();
            gradeUserList.add(grade);
        });

        return User.builder()
                .username(userEntity.getUsername())
                .report(gradeUserList)
                .build();
    }
}
