package com.sonmez.repositories;

import com.sonmez.entities.user.role.ERole;
import com.sonmez.entities.user.role.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(ERole role);
}
