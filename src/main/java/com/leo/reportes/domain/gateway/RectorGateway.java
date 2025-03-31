package com.leo.reportes.domain.gateway;

import com.leo.reportes.domain.models.User;
import com.leo.reportes.infrastructure.models.UserEntity;

import java.util.List;

public interface RectorGateway {
    List<UserEntity> getAllStudents();
    User addStudent(String studentName);
    List<User> getAllResults();
    byte[] generateGlobalReport();
}
