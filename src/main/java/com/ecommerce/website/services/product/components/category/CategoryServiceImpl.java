package com.ecommerce.website.services.product.components.category;

import com.ecommerce.website.entities.product.components.Category;
import com.ecommerce.website.exception.product.components.CategoryExistsException;
import com.ecommerce.website.repositories.product.components.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())){
            throw new CategoryExistsException(category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public boolean isExists(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean isExists(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
