package com.ecommerce.website.repositories.product;

import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends
        CrudRepository<Product, Long>,
        PagingAndSortingRepository<Product, Long> {
    boolean existsByUserAndName(User user, String productName);

    Optional<Product> findByIdAndUserId(Long id, Long userId);
}
