package com.ecommerce.website.repositories.product.components;

import com.ecommerce.website.entities.product.components.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
}
