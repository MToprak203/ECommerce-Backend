package com.sonmez.dtos.mappers.impl.product;

import com.sonmez.dtos.product.ProductDto;
import com.sonmez.dtos.product.ProductFAQDto;
import com.sonmez.dtos.product.ProductImageDto;
import com.sonmez.dtos.mappers.Mapper;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.entities.product.ProductFAQEntity;
import com.sonmez.entities.product.ProductImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapperImpl extends Mapper<ProductEntity, ProductDto> {

    private final Mapper<ProductImageEntity, ProductImageDto> productImageMapper;
    private final Mapper<ProductFAQEntity, ProductFAQDto> productFaqMapper;

    public ProductMapperImpl(ModelMapper modelMapper,
                             Mapper<ProductImageEntity, ProductImageDto> productImageMapper,
                             Mapper<ProductFAQEntity, ProductFAQDto> productFaqMapper)
    {
        super(modelMapper);
        this.productImageMapper = productImageMapper;
        this.productFaqMapper = productFaqMapper;
    }

    @Override
    public ProductDto mapTo(ProductEntity productEntity) {
        ProductDto mapped = modelMapper.map(productEntity, ProductDto.class);

        if (productEntity.getImages() != null)
        {
            mapped.setImages(productEntity
                    .getImages()
                    .stream()
                    .map(productImageMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        if (productEntity.getFaqs() != null)
        {
            mapped.setFaqs(productEntity
                    .getFaqs()
                    .stream()
                    .map(productFaqMapper::mapTo)
                    .collect(Collectors.toList())
            );
        }

        return mapped;
    }

    @Override
    public ProductEntity mapFrom(ProductDto productDto) {
        ProductEntity mapped = modelMapper.map(productDto, ProductEntity.class);

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
