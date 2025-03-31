package com.leo.reportes.domain.port;

import com.leo.reportes.domain.models.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> getAllCourses();

}
