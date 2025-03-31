package com.leo.reportes.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leo.reportes.domain.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "USERS")
    public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID")
        private long id;
        //todo añadir propiedad unique
        private String username;
        @JsonIgnore
        private boolean hasResult;
        @JsonIgnore
        private String password;
        @ManyToMany(fetch = FetchType.EAGER)
        private Set<RoleEntity> roles = new HashSet<>();
        @OneToMany(mappedBy = "userId")
        private Set<GradesEntity> courseUser = new HashSet<>();

        public User toEntity(){
            return User.builder()
                    .username(this.username)
                    //.report()//todo
                    .rol(this.roles.stream().map(RoleEntity::toDomain).collect(Collectors.toSet()))
                    .build();
        }

        public static UserEntity fromEntity(User user){
            return UserEntity.builder()
                    .username(user.getUsername())
                    .roles(RoleEntity.fromDomain(user.getRol()))
                    .build();
        }
    }
