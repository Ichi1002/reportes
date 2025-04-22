package com.leo.reportes.usecase;

import com.leo.reportes.domain.gateway.RectorGateway;
import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.domain.models.User;
import com.leo.reportes.domain.port.GradesRepository;
import com.leo.reportes.infrastructure.models.UserEntity;
import com.leo.reportes.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RectorUsecase implements RectorGateway {

    private final UserRepository userRepository;
    private final GradesRepository gradesRepository;


    @Override
    public List<User> getAllStudents() {
        return userRepository.findAllStudents(2)
                .stream()
                .map(UserEntity::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public User addStudent(String studentName) {
        User user = User.builder()
                .username(studentName)
                .build();
         return  userRepository.addStudent(user);

    }

    @Override
    public List<User> getAllResults() {
        List<UserEntity> usersEntity = userRepository.findAllStudents(2);
        List<User> userList = new ArrayList<>();
        usersEntity.forEach(userEntity -> {
            List<Grade> gradeUserList = new ArrayList<>();
            gradesRepository.findGradesByUserId(userEntity).forEach(grades -> {
                Grade grade = Grade.builder()
                        .courseName(grades.getCourseName())
                        .grade(grades.getGrade())
                        .build();
                gradeUserList.add(grade);
            });

            User user = User.builder()
                    .username(userEntity.getUsername())
                    .report(gradeUserList)
                    .build();
            userList.add(user);
        });
        return userList;
    }

    @Override
    public byte[] generateGlobalReport() {

        JasperPrint jasperPrint =  gradesRepository.generateGlobalReport();
        try {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
