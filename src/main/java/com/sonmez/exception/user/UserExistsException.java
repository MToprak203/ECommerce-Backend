package com.sonmez.exception.user;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String email)
    {
        super("A user with " + email + " email already exists!");
    }
}
