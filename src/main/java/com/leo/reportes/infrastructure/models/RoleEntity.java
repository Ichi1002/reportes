package com.leo.reportes.infrastructure.models;

import com.leo.reportes.domain.models.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.Iterator;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ROLES")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    private String name;


    static Set<RoleEntity> fromDomain(Set<Rol> rol){
        Iterator<Rol> iterator = rol.iterator();
        Rol firstRol = iterator.next();

        return Set.of(RoleEntity.builder()
                .name(firstRol.getName())
                .build());
    }

    public Rol toDomain(){
        return Rol.builder()
                        .name(this.name)
                .build();
    }

}
