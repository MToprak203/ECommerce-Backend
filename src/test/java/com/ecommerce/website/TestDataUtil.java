package com.ecommerce.website;

import com.ecommerce.website.dtos.user.auth.UserLoginDto;
import com.ecommerce.website.dtos.user.auth.UserRegisterDto;
import com.ecommerce.website.entities.product.Product;
import com.ecommerce.website.entities.product.components.ProductFAQ;
import com.ecommerce.website.entities.product.components.ProductImage;
import com.ecommerce.website.entities.user.components.Address;
import com.ecommerce.website.entities.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static Product createTestProduct()
    {
        return Product.builder()
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

    public static ProductImage createTestProductImage()
    {
        return ProductImage.builder()
                .id(1L)
                .imageUrl("imageUrl")
                .build();
    }

    public static ProductFAQ createTestProductFAQ()
    {
        return ProductFAQ.builder()
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

    public static User createTestUser()
    {

        return User.builder()
                .id(1L)
                .email("testemail@test.com")
                .fullName("testTester")
                .password("asdsfaA15!2")
                .phoneNumber("+555555555555")
                .addresses(new ArrayList<>())
                .roles(new HashSet<>())
                .build();
    }

    public static Address createTestAddress()
    {
        return Address.builder()
                .id(1L)
                .address("test address")
                .district("test district")
                .city("test city")
                .country("test country")
                .build();
    }

    public static User createTestAdmin()
    {
        return User.builder()
                .email("admin@admin.com")
                .password("adminpassword")
                .roles(new HashSet<>())
                .build();
    }
}
