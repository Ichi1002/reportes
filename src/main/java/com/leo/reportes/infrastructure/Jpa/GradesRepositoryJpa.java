package com.leo.reportes.infrastructure.Jpa;

import com.leo.reportes.infrastructure.models.GradesEntity;
import com.leo.reportes.infrastructure.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradesRepositoryJpa extends JpaRepository<GradesEntity,Long> {
    boolean existsByUserId(UserEntity id);
    List<GradesEntity> findByUserId(UserEntity id);
}
