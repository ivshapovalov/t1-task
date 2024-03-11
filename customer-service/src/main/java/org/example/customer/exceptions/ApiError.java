package org.example.customer.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@JsonIgnoreProperties
@Data
@ToString
@NoArgsConstructor
public class ApiError {
    private Object message;
    private int statusCode;
    private String statusMessage;
    private String exception;
    private String path;
    private OffsetDateTime dateTime;
}