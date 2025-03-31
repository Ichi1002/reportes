package com.leo.reportes.usecase;

import com.leo.reportes.domain.gateway.StudentGateway;
import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.domain.models.User;
import com.leo.reportes.domain.port.CourseRepository;
import com.leo.reportes.domain.port.GradesRepository;
import com.leo.reportes.domain.port.UserRepository;
import com.leo.reportes.infrastructure.models.UserEntity;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentUsecase implements StudentGateway {

    private final CourseRepository courseRepository;
    private final GradesRepository gradesRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public byte[] generateReport(long id) {
        var userEntity = userRepository.findById(id);
        if(!gradesRepository.existsGrades(userEntity)){
        Map<String,Double> gradesMap = new HashMap<>();
        courseRepository.getAllCourses().forEach(course ->{
                    double randomScore = Math.random() * 10;
                    DecimalFormat format = new DecimalFormat("#.#");
                    gradesMap.put(course.getName(),Double.parseDouble(format.format(randomScore).replace(",",".")));
                }
            );
        gradesRepository.saveGrades(id,gradesMap);
        }
        JasperPrint jasperPrint =  gradesRepository.generateReport(id);
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User generateResult(long id) {
        List<Grade> gradeUserList = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(id);
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
