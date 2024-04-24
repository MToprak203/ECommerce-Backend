package com.sonmez.exception.user;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Incorrect password!");
    }
}
