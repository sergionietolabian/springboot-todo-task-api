package com.sergionietolabian.springbootapi.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sergionietolabian.springbootapi.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<ValidationErrorResponse.FieldError> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> new ValidationErrorResponse.FieldError(
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                        .collect(Collectors.toList());

        ValidationErrorResponse response =
                new ValidationErrorResponse(400, errors);

        return ResponseEntity.badRequest().body(response);
    }
}