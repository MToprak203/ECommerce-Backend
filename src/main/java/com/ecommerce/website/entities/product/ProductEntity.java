package com.ecommerce.website.entities.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    private String name;

    private String description;

    private String thumbnailUrl;

    @Column(unique = true)
    private String barcode;

    private String modelCode;

    @Column(precision=10, scale=2)
    private BigDecimal price;

    @PositiveOrZero(message = "Product stock can not be negative.")
    private Integer stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFAQEntity> faqs;
}
