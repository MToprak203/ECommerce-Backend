package com.ecommerce.website.controllers;

import com.ecommerce.website.entities.product.components.Category;
import com.ecommerce.website.services.product.components.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/private/categories")
    @PreAuthorize("@authComponent.hasPermissionForEditCategories()")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.create(category), HttpStatus.CREATED);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/private/category/{id}")
    @PreAuthorize("@authComponent.hasPermissionForEditCategories()")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody Category category)
    {
        if (!categoryService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
    }

    @DeleteMapping("/private/category/{id}")
    @PreAuthorize("@authComponent.hasPermissionForEditCategories()")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id)
    {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
