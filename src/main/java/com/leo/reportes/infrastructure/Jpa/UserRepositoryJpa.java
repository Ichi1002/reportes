package com.leo.reportes.infrastructure.Jpa;


import com.leo.reportes.infrastructure.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    List<UserEntity> findAllByRolesId(long id);
}
