package com.ecommerce.website.repositories.product.components;

import com.ecommerce.website.entities.product.components.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByName(String name);
}
