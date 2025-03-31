package com.leo.reportes.infrastructure.config;

import com.leo.reportes.infrastructure.Jpa.RolRepositoryJpa;
import com.leo.reportes.infrastructure.Jpa.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitLoad {

    private final UserRepositoryJpa userRepositoryJpa;
    private final RolRepositoryJpa rolRepositoryJpa;

    /*@PostConstruct
    private void loadDb(){
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1)
                .name("RECTOR")
                .build();


        UserEntity user = UserEntity.builder()
                .username("rector")
                .password("$2a$10$DrU.BvZS4IvjFoete0MwOO6qThQOEyWQvUv6Kl/zupW0zMrbAGTz2")
                .roles(Set.of(userRoleEntity))
                .build();
        rolRepositoryJpa.save(userRoleEntity);
        userRepositoryJpa.save(user);
    }*/
}
