package com.ecommerce.website.repositories;

import com.ecommerce.website.entities.product.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends
        CrudRepository<ProductEntity, Long>,
        PagingAndSortingRepository<ProductEntity, Long>
{
    boolean existsByBarcode(String barcode);
}
