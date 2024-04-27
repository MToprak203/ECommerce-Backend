package com.ecommerce.website.services.product;

import com.ecommerce.website.entities.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService{
    ProductEntity create(ProductEntity product);

    ProductEntity update(ProductEntity product);

    List<ProductEntity> findAll();

    Page<ProductEntity> findAll(Pageable pageable);

    Optional<ProductEntity> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
