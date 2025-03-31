package com.leo.reportes.infrastructure.models;


import com.leo.reportes.domain.models.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String course;

    @Column(name = "course_translation")
    private String courseTranslation;

    public Course toDomain(){
        return Course.builder()
                .name(this.course)
                .build();
    }
}