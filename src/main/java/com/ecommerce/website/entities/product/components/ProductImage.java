package com.ecommerce.website.entities.product.components;

import com.ecommerce.website.entities.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ProductImages")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private Boolean isThumbnail;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
