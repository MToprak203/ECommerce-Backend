package com.ecommerce.website.services.product;

import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.product.components.Brand;
import com.ecommerce.website.entities.product.components.Category;
import com.ecommerce.website.exception.product.ProductExistsException;
import com.ecommerce.website.repositories.product.ProductRepository;
import com.ecommerce.website.repositories.product.components.BrandRepository;
import com.ecommerce.website.repositories.product.components.CategoryRepository;
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

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product create(Product product) {
        if (productRepository.existsByUserAndName(product.getUser(), product.getName())) {
            throw new ProductExistsException(product.getName());
        }

        Optional<Brand> brandOpt = brandRepository.findByName(product.getBrand().getName());
        brandOpt.ifPresent(product::setBrand);

        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return StreamSupport.stream(productRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOne(Long id) {
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
