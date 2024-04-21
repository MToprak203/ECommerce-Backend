package com.sonmez.repositories;

import com.sonmez.entities.ProductImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageEntity, Long> {
}
