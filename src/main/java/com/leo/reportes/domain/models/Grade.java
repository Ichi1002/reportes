package com.leo.reportes.domain.models;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grade {
    private String courseName;
    private Double grade;
}
