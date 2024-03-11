package org.example.supplier.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Log
class ExceptionHandlerAdvice {

    @ExceptionHandler(
            {
                    BusinessException.class,
                    DataIntegrityViolationException.class,
                    HttpMessageNotReadableException.class,
                    MethodArgumentTypeMismatchException.class
            }
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleSupplierCommonExceptions(Exception ex, HttpServletRequest req) {
        return getApiError(ex.getMessage(), ex, req);
    }

    private ApiError getApiError(Object message, Exception ex, HttpServletRequest req) {
        return ApiError.builder()
                .message(message)
                .statusCode(400)
                .statusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .exception(ex.getClass().getName())
                .path(req.getRequestURI())
                .dateTime(OffsetDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiError handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return getApiError(errors, ex, req);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ApiError handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest req) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();
        constraintViolations.forEach(violation ->
        {
            String[] path = violation.getPropertyPath().toString().split("\\.");
            errors.put(path[path.length - 1], violation.getMessage());
        });
        return getApiError(errors, ex, req);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleAllUncaughtException(Exception ex, HttpServletRequest req) {
        return getApiError(ex.getMessage(), ex, req);
    }

}
