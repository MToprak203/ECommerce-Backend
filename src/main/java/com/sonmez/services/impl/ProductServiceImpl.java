package com.sonmez.services.impl;

import com.sonmez.entities.ProductEntity;
import com.sonmez.repositories.ProductRepository;
import com.sonmez.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity save(ProductEntity product) {
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
    public Optional<ProductEntity> findOne(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return !productRepository.existsById(id);
    }

    @Override
    public ProductEntity partialUpdate(Long id, ProductEntity productEntity) {
        productEntity.setId(id);

        return productRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(productEntity.getName()).ifPresent(existingProduct::setName);
            Optional.ofNullable(productEntity.getDescription()).ifPresent(existingProduct::setDescription);
            Optional.ofNullable(productEntity.getBarcode()).ifPresent(existingProduct::setBarcode);
            Optional.ofNullable(productEntity.getModelCode()).ifPresent(existingProduct::setModelCode);
            Optional.ofNullable(productEntity.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(productEntity.getStock()).ifPresent(existingProduct::setStock);
            Optional.ofNullable(productEntity.getImages()).ifPresent(images -> {
                existingProduct.getImages().clear();
                existingProduct.getImages().addAll(images);
            });
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product does not exists"));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
