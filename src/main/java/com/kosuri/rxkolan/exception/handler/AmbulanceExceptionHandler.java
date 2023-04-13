package com.kosuri.rxkolan.exception.handler;

import com.kosuri.rxkolan.exception.InternalException;
import com.kosuri.rxkolan.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AmbulanceExceptionHandler extends GlobalExceptionHandler {


    @ExceptionHandler({InternalException.class})
    public ResponseEntity<ErrorResponse> handleInternalException(InternalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getErrorResponse());
    }

}