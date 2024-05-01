package com.ecommerce.website.security.components;

import com.ecommerce.website.entities.user.components.role.Role;
import com.ecommerce.website.repositories.product.ProductRepository;
import com.ecommerce.website.repositories.user.UserRepository;
import com.ecommerce.website.repositories.user.components.RoleRepository;
import com.ecommerce.website.security.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
public class AuthComponent {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    private UserDetailsImpl getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthenticatedUser().getAuthorities();
    }

    public boolean hasPermission(String permissionName) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        return authorities.stream()
                .map(auth -> roleRepository.findByName(auth.getAuthority()))
                .anyMatch(role -> hasPermission(role, permissionName));
    }

    private boolean hasPermission(Role role, String permissionName) {
        return switch (permissionName) {
            case "EditAllProducts" -> role.getEditAllProductsPermission();
            case "EditSelfProducts" -> role.getEditSelfProductsPermission();
            case "EditAllUsers" -> role.getEditAllUsersPermission();
            case "EditSelfUser" -> role.getEditSelfUserPermission();
            case "EditProductCategories" -> role.getEditProductCategoriesPermission();
            default -> false;
        };
    }

    public boolean hasPermissionForEditProducts(Long productId) {
        UserDetailsImpl user = getAuthenticatedUser();
        if (hasPermission("EditAllProducts")) return true;
        return hasPermission("EditSelfProducts") &&
                productRepository.findByIdAndUserId(productId, user.getId()).isPresent();
    }

    public boolean hasPermissionForEditUsers(Long userId) {
        UserDetailsImpl user = getAuthenticatedUser();
        if(hasPermission("EditAllUsers")) return true;
        return hasPermission("EditSelfUser") &&
                Objects.equals(user.getId(), userId);
    }

    public boolean hasPermissionForCreateProduct() {
        return hasPermission("EditAllProducts") || hasPermission("EditSelfProducts");
    }

    public boolean hasPermissionForEditCategories() {
        return hasPermission("EditProductCategories");
    }
}
