package com.ecommerce.website.repositories;

import com.ecommerce.website.entities.user.role.ERole;
import com.ecommerce.website.entities.user.role.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(ERole role);
}
