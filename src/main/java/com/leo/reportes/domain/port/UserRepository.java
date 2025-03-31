package com.leo.reportes.domain.port;

import com.leo.reportes.domain.models.User;
import com.leo.reportes.infrastructure.models.UserEntity;

import java.util.List;

public interface UserRepository {

    User addStudent(User user);
    List<UserEntity> findAllStudents(long rolId);
    UserEntity findById(long id);

}
