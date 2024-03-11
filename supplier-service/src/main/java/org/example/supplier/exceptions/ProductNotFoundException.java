package org.example.supplier.exceptions;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(Long parcelId) {
        super(String.format("Could not find product with id '%s'", parcelId));
    }
}
