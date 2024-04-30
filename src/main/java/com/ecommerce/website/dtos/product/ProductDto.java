package com.ecommerce.website.dtos.product;

import com.ecommerce.website.dtos.product.components.BrandDto;
import com.ecommerce.website.dtos.product.components.ProductFAQDto;
import com.ecommerce.website.dtos.product.components.ProductImageDto;
import com.ecommerce.website.dtos.user.UserDto;
import com.ecommerce.website.dtos.user.UserMetadataDto;
import com.ecommerce.website.entities.product.components.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotNull(message = "Product name can not be empty.")
    @NotBlank(message = "Product name can not be empty.")
    @Size(max = 150, message = "Product name can not be longer than 150 characters")
    private String name;

    @NotNull(message = "Product description can not be empty.")
    @NotBlank(message = "Product description can not be empty.")
    private String description;

    private String thumbnailUrl;

    @PositiveOrZero(message = "Product price can not be negative.")
    private BigDecimal price;

    @PositiveOrZero(message = "Product stock can not be negative.")
    private Integer stock;

    private BrandDto brand;

    @NotNull
    private UserMetadataDto user;

    @NotNull(message = "Categories can not be empty.")
    @NotBlank(message = "Product description can not be empty.")
    private Set<Category> categories;

    private List<ProductImageDto> images;

    private List<ProductFAQDto> faqs;
}
