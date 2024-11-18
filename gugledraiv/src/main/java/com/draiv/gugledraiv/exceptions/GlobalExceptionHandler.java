package com.draiv.gugledraiv.exceptions;

import java.nio.file.NoSuchFileException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchFileException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "Error: No se encontró el recurso",
            ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }*/

    //Maneja las excepciones BadRequestException (error 400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getMessage(), 
            HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

