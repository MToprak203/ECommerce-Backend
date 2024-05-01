package com.ecommerce.website.services.product;

import com.ecommerce.website.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(Product product);

    Product update(Product product);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
