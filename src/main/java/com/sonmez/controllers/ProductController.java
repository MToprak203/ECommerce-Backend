package com.sonmez.controllers;

import com.sonmez.dtos.ProductDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.ProductEntity;
import com.sonmez.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductService productService;
    private final Mapper<ProductEntity, ProductDto> productMapper;

    public ProductController(ProductService productService, Mapper<ProductEntity, ProductDto> productMapper)
    {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping(path = "/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(productMapper.mapTo(savedProductEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductDto>> listProducts()
    {
        List<ProductEntity> productEntities = productService.findAll();
        return new ResponseEntity<>(
                productEntities.stream()
                        .map(productMapper::mapTo)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
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

    @PutMapping(path = "products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto)
    {
        if (productService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productDto.setId(id);
        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity savedProductEntity = productService.save(productEntity);
        return new ResponseEntity<>(
                productMapper.mapTo(savedProductEntity),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "products/{id}")
    public ResponseEntity<ProductDto> partialUpdate(@PathVariable("id") Long id, @RequestBody ProductDto productDto)
    {
        if (productService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ProductEntity productEntity = productMapper.mapFrom(productDto);
        ProductEntity updatedProduct =  productService.partialUpdate(id, productEntity);
        return new ResponseEntity<>(productMapper.mapTo(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping(path = "products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id)
    {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
