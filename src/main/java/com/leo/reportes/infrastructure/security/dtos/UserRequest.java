package com.leo.reportes.infrastructure.security.dtos;


import com.leo.reportes.infrastructure.models.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private Set<RoleEntity> roles;
}