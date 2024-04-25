package com.sonmez;

import com.sonmez.dtos.user.UserLoginDto;
import com.sonmez.dtos.user.UserRegisterDto;
import com.sonmez.entities.product.ProductEntity;
import com.sonmez.entities.product.ProductFAQEntity;
import com.sonmez.entities.product.ProductImageEntity;
import com.sonmez.entities.user.AddressEntity;
import com.sonmez.entities.user.UserEntity;
import com.sonmez.entities.user.role.RoleEntity;
import com.sonmez.entities.user.role.Roles;

import java.math.BigDecimal;
import java.util.ArrayList;

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
                .roles(new ArrayList<>())
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

    public static RoleEntity createTestRole()
    {
        return RoleEntity.builder()
                .id(1L)
                .role(Roles.USER.toString())
                .build();
    }
}
