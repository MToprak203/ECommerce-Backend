package com.ecommerce.website.services.product;

import com.ecommerce.website.entities.product.ProductEntity;
import com.ecommerce.website.exception.product.ProductExistsException;
import com.ecommerce.website.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity create(ProductEntity product) {
        if (productRepository.existsByBarcode(product.getBarcode()))
        {
            throw new ProductExistsException(product.getBarcode());
        }
        return productRepository.save(product);
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> findAll() {
        return StreamSupport.stream(productRepository
                                .findAll()
                                .spliterator(),
                                false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductEntity> findOne(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
