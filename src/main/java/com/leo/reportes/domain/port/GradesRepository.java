package com.leo.reportes.domain.port;

import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.infrastructure.models.UserEntity;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GradesRepository {
    void saveGrades(Long id, Map<String,Double> grades);

    JasperPrint generateReport(long id);

    boolean existsGrades(UserEntity id);

    List<Grade> findGradesByUserId(UserEntity userEntity);

    JasperPrint generateGlobalReport();
}
