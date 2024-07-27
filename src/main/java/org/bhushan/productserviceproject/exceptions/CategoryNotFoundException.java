package org.bhushan.productserviceproject.exceptions;

import org.bhushan.productserviceproject.dtos.ErrorDto;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
