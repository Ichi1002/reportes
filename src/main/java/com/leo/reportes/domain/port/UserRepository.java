package com.leo.reportes.domain.port;

import com.leo.reportes.domain.models.User;
import com.leo.reportes.infrastructure.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User addStudent(User user);
    List<UserEntity> findAllStudents(long rolId);
    Optional<UserEntity> findById(long id);

}
