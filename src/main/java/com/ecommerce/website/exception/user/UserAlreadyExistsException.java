package com.ecommerce.website.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("A user with " + email + " email already exists!");
    }
}
