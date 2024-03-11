package org.example.supplier.exceptions;

import static java.lang.String.format;

public class ReviewNotFoundException extends BusinessException {
    public ReviewNotFoundException(Long categoryId) {
        super(format("Could not find review with id '%s'", categoryId));
    }
}
