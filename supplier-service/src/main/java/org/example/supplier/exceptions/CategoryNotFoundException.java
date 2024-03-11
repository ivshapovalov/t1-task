package org.example.supplier.exceptions;

import static java.lang.String.format;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(Long categoryId) {
        super(format("Could not find category with id '%s'", categoryId));
    }
}
