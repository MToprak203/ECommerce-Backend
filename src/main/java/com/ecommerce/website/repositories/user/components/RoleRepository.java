package com.ecommerce.website.repositories.user.components;

import com.ecommerce.website.entities.user.role.ERole;
import com.ecommerce.website.entities.user.role.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(ERole role);
}
