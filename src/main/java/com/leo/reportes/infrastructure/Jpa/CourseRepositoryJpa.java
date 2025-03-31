package com.leo.reportes.infrastructure.Jpa;

import com.leo.reportes.infrastructure.models.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepositoryJpa extends JpaRepository<CourseEntity,Long> {
    CourseEntity findByCourse(String courseName);

}
