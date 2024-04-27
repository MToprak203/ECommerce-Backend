package com.ecommerce.website.controllers;

import com.ecommerce.website.dtos.product.ProductDto;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.ProductMetadataDto;
import com.ecommerce.website.entities.product.ProductEntity;
import com.ecommerce.website.services.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Mapper<ProductEntity, ProductDto> productMapper;
    @Autowired
    private Mapper<ProductEntity, ProductMetadataDto> productMetadataMapper;


    @PostMapping("/admin/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto)
    {
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.create(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductMetadataDto>> listProducts(Pageable pageable)
    {
        Page<ProductEntity> productEntities = productService.findAll(pageable);
        return new ResponseEntity<>(productEntities.map(productMetadataMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id)
    {
        Optional<ProductEntity> foundProduct = productService.findOne(id);
        return foundProduct.map(productEntity -> {
            ProductDto productDto = productMapper.mapTo(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto)
    {
        if (!productService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productDto.setId(id);
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.update(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id)
    {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
