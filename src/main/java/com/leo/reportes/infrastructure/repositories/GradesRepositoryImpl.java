package com.leo.reportes.infrastructure.repositories;

import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.domain.port.GradesRepository;
import com.leo.reportes.infrastructure.Jpa.CourseRepositoryJpa;
import com.leo.reportes.infrastructure.Jpa.GradesRepositoryJpa;
import com.leo.reportes.infrastructure.Jpa.UserRepositoryJpa;
import com.leo.reportes.infrastructure.models.GradesEntity;
import com.leo.reportes.infrastructure.models.UserEntity;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GradesRepositoryImpl implements GradesRepository {
    private final GradesRepositoryJpa gradesRepositoryJpa;
    private final CourseRepositoryJpa courseRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private Connection connection;

    @Override
    public void saveGrades(Long id, Map<String, Double> grades) {

        List<GradesEntity> gradeList = new ArrayList<>();
        grades.forEach((courseName, grade) -> {
                    var gradeEntity = new GradesEntity();
                    var courseEntity = courseRepositoryJpa.findByCourse(courseName);
                    var userEntity = userRepositoryJpa.findById(id);
                    gradeEntity.setCurseId(courseEntity);
                    gradeEntity.setUserId(userEntity.get());
                    gradeEntity.setGrade(grade);
                    gradeList.add(gradeEntity);
                }


        );
        gradesRepositoryJpa.saveAll(gradeList);
    }

    @Override
    public JasperPrint generateReport(long id) {
        Map<String, Object> empParams = new HashMap<String, Object>();
        empParams.put("CompanyName", "TechGeekNext");
        empParams.put("id", id);
        try {
            File file = ResourceUtils.getFile("classpath:studentReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            return JasperFillManager.fillReport(jasperReport, empParams
                    ,connection);

        } catch (FileNotFoundException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsGrades(UserEntity id) {
        return gradesRepositoryJpa.existsByUserId(id);
    }

    @Override
    public List<Grade> findGradesByUserId(UserEntity userEntity) {
        return gradesRepositoryJpa.findByUserId(userEntity)
                .stream()
                .map(GradesEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public JasperPrint generateGlobalReport() {
        Map<String, Object> empParams = new HashMap<String, Object>();

        try {
            File file = ResourceUtils.getFile("classpath:globalReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            return JasperFillManager.fillReport(jasperReport, empParams
                    ,connection);

        } catch (FileNotFoundException | JRException e) {
            throw new RuntimeException(e);
        }
    }


}
