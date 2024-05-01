package com.ecommerce.website.exception.product;

public class ProductExistsException extends RuntimeException {
    public ProductExistsException(String productName) {
        super("User has already selling product named: " + productName);
    }
}
