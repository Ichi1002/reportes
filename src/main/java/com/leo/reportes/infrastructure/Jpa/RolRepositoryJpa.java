package com.leo.reportes.infrastructure.Jpa;


import com.leo.reportes.infrastructure.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositoryJpa extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
