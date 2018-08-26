package com.yieldBroker.controller;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return strBuilder.toString();
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(RuntimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(DataAccessException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}