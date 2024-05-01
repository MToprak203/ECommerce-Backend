package com.ecommerce.website.repositories.user;

import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
