package com.ecommerce.website;

import com.ecommerce.website.dtos.user.SignInResultDto;
import com.ecommerce.website.dtos.user.UserLoginDto;
import com.ecommerce.website.dtos.user.UserRegisterDto;
import com.ecommerce.website.entities.product.ProductEntity;
import com.ecommerce.website.entities.product.ProductFAQEntity;
import com.ecommerce.website.entities.product.ProductImageEntity;
import com.ecommerce.website.entities.user.AddressEntity;
import com.ecommerce.website.entities.user.UserEntity;
import com.ecommerce.website.entities.user.role.RoleEntity;
import com.ecommerce.website.entities.user.role.ERole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static ProductEntity createTestProduct()
    {
        return ProductEntity.builder()
                .id(1L)
                .name("product")
                .description("product description")
                .thumbnailUrl("product thumbnailUrl")
                .barcode("product barcode")
                .modelCode("product modelcode")
                .price(BigDecimal.valueOf(1000.15).setScale(2))
                .stock(10)
                .images(new ArrayList<>())
                .faqs(new ArrayList<>())
                .build();
    }

    public static ProductImageEntity createTestProductImage()
    {
        return ProductImageEntity.builder()
                .id(1L)
                .imageUrl("imageUrl")
                .build();
    }

    public static ProductFAQEntity createTestProductFAQ()
    {
        return ProductFAQEntity.builder()
                .id(1L)
                .question("question")
                .answer("answer")
                .build();
    }

    public static UserRegisterDto createTestUserRegisterDto()
    {
        return UserRegisterDto.builder()
                .email("testemail@test.com")
                .password("asdsfaA15!2")
                .fullName("testTester")
                .phoneNumber("+555555555555")
                .build();
    }

    public static UserLoginDto createTestUserLoginDto()
    {
        return UserLoginDto.builder()
                .email("testemail@test.com")
                .password("asdsfaA15!2")
                .build();
    }

    public static UserEntity createTestUser()
    {

        return UserEntity.builder()
                .id(1L)
                .email("testemail@test.com")
                .fullName("testTester")
                .password("asdsfaA15!2")
                .phoneNumber("+555555555555")
                .addresses(new ArrayList<>())
                .roles(new HashSet<>())
                .build();
    }

    public static AddressEntity createTestAddress()
    {
        return AddressEntity.builder()
                .id(1L)
                .address("test address")
                .district("test district")
                .city("test city")
                .country("test country")
                .build();
    }

    public static UserEntity createTestAdmin()
    {
        return UserEntity.builder()
                .email("admin@admin.com")
                .password("adminpassword")
                .roles(new HashSet<>())
                .build();
    }
}
