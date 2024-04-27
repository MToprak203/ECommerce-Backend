package com.ecommerce.website.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User not found with " + email + " email!");
    }
}
