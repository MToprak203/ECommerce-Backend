package com.sonmez.repositories;

import com.sonmez.entities.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends
        CrudRepository<ProductEntity, Long>,
        PagingAndSortingRepository<ProductEntity, Long>
{
}
