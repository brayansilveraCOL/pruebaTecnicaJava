package com.core.api.Exception;

import com.core.api.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> {
                    assert fe.getDefaultMessage() != null;
                    return Map.of(
                            "field", fe.getField(),
                            "rejected", String.valueOf(fe.getRejectedValue()),
                            "message", fe.getDefaultMessage()
                    );
                })
                .toList();
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage("VALIDATION_ERROR");
        dto.setData(errors);
        dto.setSuccess(Boolean.FALSE);
        return dto;
    }
}
