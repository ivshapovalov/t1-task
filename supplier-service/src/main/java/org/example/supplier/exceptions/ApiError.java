package org.example.supplier.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@JsonIgnoreProperties
@Data
@Builder
public class ApiError {
    private Object message;
    private int statusCode;
    private String statusMessage;
    private String exception;
    private String path;
    private OffsetDateTime dateTime;
}