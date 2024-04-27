package com.ecommerce.website.repositories;

import com.ecommerce.website.entities.product.ProductImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageEntity, Long> {
}
