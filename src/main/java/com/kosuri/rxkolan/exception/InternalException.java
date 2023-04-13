package com.kosuri.rxkolan.exception;

import com.kosuri.rxkolan.model.error.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class InternalException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;

    public InternalException(HttpStatus status, String code, String message) {
        super(message);

        this.httpStatus = status;
        this.code = code;
    }

    public ErrorResponse getErrorResponse() {
        return ErrorResponse.builder()
                .success(false)
                .errorList(List.of(
                        ErrorResponse.Error.builder()
                                .errorMessage(getMessage())
                                .errorCode(code)
                                .build()
                )).build();
    }
}