package com.ecommerce.website.repositories.product.components;

import com.ecommerce.website.entities.product.components.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {
    Optional<Brand> findByName(String name);
}
