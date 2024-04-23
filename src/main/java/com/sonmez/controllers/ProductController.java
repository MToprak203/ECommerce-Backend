package com.sonmez.controllers;

import com.sonmez.dtos.product.ProductDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.services.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final Mapper<ProductEntity, ProductDto> productMapper;

    public ProductController(ProductService productService, Mapper<ProductEntity, ProductDto> productMapper)
    {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto)
    {
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> listProducts(Pageable pageable)
    {
        Page<ProductEntity> productEntities = productService.findAll(pageable);
        return new ResponseEntity<>(productEntities.map(productMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id)
    {
        Optional<ProductEntity> foundProduct = productService.findOne(id);
        return foundProduct.map(productEntity -> {
            ProductDto productDto = productMapper.mapTo(productEntity);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto)
    {
        if (productService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productDto.setId(id);
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id)
    {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
