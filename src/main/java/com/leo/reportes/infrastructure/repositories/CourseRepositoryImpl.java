package com.leo.reportes.infrastructure.repositories;

import com.leo.reportes.domain.models.Course;
import com.leo.reportes.domain.port.CourseRepository;
import com.leo.reportes.infrastructure.Jpa.CourseRepositoryJpa;
import com.leo.reportes.infrastructure.models.CourseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseRepositoryJpa courseRepositoryJpa;
    @Override
    public List<Course> getAllCourses() {
        return courseRepositoryJpa.findAll()
                .stream()
                .map(CourseEntity::toDomain)
                .collect(Collectors.toList());
    }
}
