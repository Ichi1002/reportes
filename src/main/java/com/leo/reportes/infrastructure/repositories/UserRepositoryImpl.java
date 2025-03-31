package com.leo.reportes.infrastructure.repositories;

import com.leo.reportes.domain.models.User;
import com.leo.reportes.infrastructure.Jpa.RolRepositoryJpa;
import com.leo.reportes.infrastructure.Jpa.UserRepositoryJpa;
import com.leo.reportes.infrastructure.models.UserEntity;
import com.leo.reportes.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;
    private final RolRepositoryJpa rolRepositoryJpa;

    @Override
    public User addStudent(User user) {
        var rolEntity = rolRepositoryJpa.findByName("STUDENT");
        UserEntity userEntity = UserEntity.builder()
                .roles(Set.of(rolEntity))
                .username(user.getUsername())
                .password("$2a$10$tgaMSYqiHknY6yU.yzRS3OzG7m1fZrKJ6xgliT2P3VedL40aO48Lm")
                .build();
        return userRepositoryJpa.save(userEntity).toEntity();
    }

    @Override
    public List<UserEntity> findAllStudents(long rolId) {
        return userRepositoryJpa.findAllByRolesId(rolId);
    }

    @Override
    public UserEntity findById(long id) {
        return userRepositoryJpa.findById(id).get();
    }
}
