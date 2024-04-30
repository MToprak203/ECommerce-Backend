package com.ecommerce.website.dtos.mappers.impl.product;

import com.ecommerce.website.dtos.product.ProductDto;
import com.ecommerce.website.dtos.product.components.BrandDto;
import com.ecommerce.website.dtos.product.components.ProductFAQDto;
import com.ecommerce.website.dtos.product.components.ProductImageDto;
import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.user.UserMetadataDto;
import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.product.components.Brand;
import com.ecommerce.website.entities.product.components.ProductFAQ;
import com.ecommerce.website.entities.product.components.ProductImage;
import com.ecommerce.website.entities.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapperImpl extends Mapper<Product, ProductDto> {

    @Autowired
    private Mapper<ProductImage, ProductImageDto> productImageMapper;

    @Autowired
    private Mapper<ProductFAQ, ProductFAQDto> productFaqMapper;

    @Autowired
    private Mapper<Brand, BrandDto> brandMapper;

    @Autowired
    private Mapper<User, UserMetadataDto> userMetadataMapper;

    public ProductMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductDto mapTo(Product product) {
        ProductDto mapped = modelMapper.map(product, ProductDto.class);
        mapped.setBrand(brandMapper.mapTo(product.getBrand()));
        mapped.setUser(userMetadataMapper.mapTo(product.getUser()));

        if (product.getImages() != null)
        {
            mapped.setImages(product
                    .getImages()
                    .stream()
                    .map(productImageMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        if (product.getFaqs() != null)
        {
            mapped.setFaqs(product
                    .getFaqs()
                    .stream()
                    .map(productFaqMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        return mapped;
    }

    @Override
    public Product mapFrom(ProductDto productDto) {
        Product mapped = modelMapper.map(productDto, Product.class);
        mapped.setBrand(brandMapper.mapFrom(productDto.getBrand()));
        mapped.setUser(userMetadataMapper.mapFrom(productDto.getUser()));

        if (productDto.getImages() != null)
        {
            mapped.setImages(productDto
                    .getImages()
                    .stream()
                    .map(productImageMapper::mapFrom)
                    .collect(Collectors.toList())
            );

            mapped.getImages().forEach((i) -> i.setProduct(mapped));
        }

        if (productDto.getFaqs() != null)
        {
            mapped.setFaqs(productDto
                    .getFaqs()
                    .stream()
                    .map(productFaqMapper::mapFrom)
                    .collect(Collectors.toList())
            );

            mapped.getFaqs().forEach((f) -> f.setProduct(mapped));
        }

        return mapped;
    }
}
