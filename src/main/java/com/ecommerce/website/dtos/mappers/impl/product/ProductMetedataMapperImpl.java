package com.ecommerce.website.dtos.mappers.impl.product;

import com.ecommerce.website.dtos.mappers.Mapper;
import com.ecommerce.website.dtos.product.ProductMetadataDto;
import com.ecommerce.website.dtos.product.components.BrandDto;
import com.ecommerce.website.dtos.product.components.ProductImageDto;
import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.product.components.Brand;
import com.ecommerce.website.entities.product.components.ProductImage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMetedataMapperImpl extends Mapper<Product, ProductMetadataDto> {

    @Autowired
    private Mapper<ProductImage, ProductImageDto> imageMapper;

    @Autowired
    private Mapper<Brand, BrandDto> brandMapper;

    public ProductMetedataMapperImpl(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ProductMetadataDto mapTo(Product product) {
        ProductMetadataDto mapped = modelMapper.map(product, ProductMetadataDto.class);
        mapped.setBrand(brandMapper.mapTo(product.getBrand()));

        Optional<ProductImage> thumbnailOpt = product
                .getImages()
                .stream()
                .filter(ProductImage::getIsThumbnail)
                .findFirst();

        thumbnailOpt.ifPresent(productImage -> mapped.setThumbnail(imageMapper.mapTo(productImage)));
        return mapped;
    }

    @Override
    public Product mapFrom(ProductMetadataDto productMetadataDto) {
        Product mapped = modelMapper.map(productMetadataDto, Product.class);
        mapped.setBrand(brandMapper.mapFrom(productMetadataDto.getBrand()));
        return mapped;
    }
}
