package com.sonmez.entities.user;

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
@Table(name = "Users")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    private String address;

    private String district;

    private String city;

    private String country;

    private Boolean isBillingAddress;

    private Boolean isDeliveryAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
