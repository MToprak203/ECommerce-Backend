package com.sonmez;

import com.sonmez.dtos.ProductDto;
import com.sonmez.entities.ProductEntity;
import com.sonmez.entities.ProductImageEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static ProductEntity createTestProduct1()
    {
        return ProductEntity.builder()
                .id(1L)
                .name("product1")
                .description("product1 description")
                .barcode("product1 barcode")
                .modelCode("product1 modelcode")
                .price(BigDecimal.valueOf(1000.15).setScale(2))
                .images(new ArrayList<>())
                .build();
    }

    public static ProductEntity createTestProduct2()
    {
        return ProductEntity.builder()
                .id(2L)
                .name("product2")
                .description("product2 description")
                .barcode("product2 barcode")
                .modelCode("product2 modelcode")
                .price(BigDecimal.valueOf(2000.15).setScale(2))
                .images(new ArrayList<>())
                .build();
    }

    public static ProductEntity createTestProduct3()
    {
        return ProductEntity.builder()
                .id(3L)
                .name("product3")
                .description("product3 description")
                .barcode("product3 barcode")
                .modelCode("product3 modelcode")
                .price(BigDecimal.valueOf(3000.15).setScale(2))
                .images(new ArrayList<>())
                .build();
    }

    public static ProductDto createTestProductDto1()
    {
        return ProductDto.builder()
                .id(1L)
                .name("product1 dto")
                .description("product1 dto description")
                .barcode("product1 dto barcode")
                .modelCode("product1 dto modelcode")
                .price(BigDecimal.valueOf(1000.15).setScale(2))
                .images(new ArrayList<>())
                .build();
    }

    public static ProductImageEntity createTestProductImage1()
    {
        return ProductImageEntity.builder()
                .id(1L)
                .imageUrl("image1Url")
                .build();
    }

    public static ProductImageEntity createTestProductImage2()
    {
        return ProductImageEntity.builder()
                .id(2L)
                .imageUrl("image2Url")
                .build();
    }

    public static ProductImageEntity createTestProductImage3()
    {
        return ProductImageEntity.builder()
                .id(3L)
                .imageUrl("image3Url")
                .build();
    }
}
