package org.bhushan.productserviceproject.advices;

import org.bhushan.productserviceproject.dtos.ErrorDto;
import org.bhushan.productserviceproject.exceptions.CategoryNotFoundException;
import org.bhushan.productserviceproject.exceptions.InvalidLimitException;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(productNotFoundException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLimitException.class)
    public ResponseEntity<ErrorDto> handleInvalidLimitException(InvalidLimitException invalidLimitException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(invalidLimitException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(categoryNotFoundException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
