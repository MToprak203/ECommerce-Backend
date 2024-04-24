package com.sonmez.exception.product;

public class ProductExistsException extends RuntimeException {
    public ProductExistsException(String barcode)
    {
        super("Product exists with " + barcode + " barcode");
    }
}
