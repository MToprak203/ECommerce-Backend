package com.ecommerce.website.entities.user.components.role;

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
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    private Boolean editAllProductsPermission;
    private Boolean editSelfProductsPermission;

    private Boolean editAllUsersPermission;
    private Boolean editSelfUserPermission;

    private Boolean editProductCategoriesPermission;
}
