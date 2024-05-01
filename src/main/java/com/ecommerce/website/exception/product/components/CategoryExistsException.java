package com.ecommerce.website.exception.product.components;

public class CategoryExistsException extends RuntimeException{
    public CategoryExistsException(String name){
        super("Category named " + name + " already exists!");
    }
}
