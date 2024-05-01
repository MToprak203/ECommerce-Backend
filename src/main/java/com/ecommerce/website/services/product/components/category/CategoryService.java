package com.ecommerce.website.services.product.components.category;

import com.ecommerce.website.entities.product.components.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category);

    List<Category> findAll();

    boolean isExists(Long id);

    boolean isExists(String name);

    void delete(Long id);

}
