package com.ecommerce.website.exception;

import com.ecommerce.website.exception.product.ProductExistsException;
import com.ecommerce.website.exception.user.UserAlreadyExistsException;
import com.ecommerce.website.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(RuntimeException ex)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectCredentialsExpections()
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", "Incorrect email or password!");
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, ProductExistsException.class})
    public ResponseEntity<Map<String, String>> handleRegisterExceptions(RuntimeException ex)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAuthorizationExceptions(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
