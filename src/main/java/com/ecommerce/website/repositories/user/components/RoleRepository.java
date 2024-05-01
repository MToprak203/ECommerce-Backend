package com.ecommerce.website.repositories.user.components;

import com.ecommerce.website.entities.user.components.role.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String role);
}
