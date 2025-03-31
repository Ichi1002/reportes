package com.leo.reportes.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String username;
    private List<Grade> report;
    @JsonIgnore
    private Set<Rol> rol;
}
