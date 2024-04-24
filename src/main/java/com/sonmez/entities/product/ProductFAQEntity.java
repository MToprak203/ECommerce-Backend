package com.sonmez.entities.product;

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
@Table(name = "ProductFAQs")
public class ProductFAQEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    private String question;

    private String answer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
