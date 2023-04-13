package com.kosuri.rxkolan.exception.handler;

import com.kosuri.rxkolan.exception.BadRequestException;
import com.kosuri.rxkolan.exception.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String error = "Error writing JSON output";
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Name {} parameter {} ", exception.getParameter(), exception.getParameter(), exception);
        BindingResult bindingResult = exception.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getRejectedValue() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        return buildResponseEntity(new ApiError(BAD_REQUEST, errorMessage, exception));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleConverterErrors(MethodArgumentTypeMismatchException exception) {
        log.error("Name {} parameter {} ", exception.getName(), exception.getParameter(), exception);
        Throwable cause = exception.getCause() // First cause is a ConversionException
                .getCause(); // Second Cause is your custom exception or some other exception e.g. NullPointerException
        return ResponseEntity.badRequest().body("Bad Request: [" + cause.getMessage() + "]");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(BAD_REQUEST, ex.getMessage(), ex));
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex      - NoHandlerFoundException
     * @param headers - HttpHeaders
     * @param status  - HttpStatus
     * @param request - WebRequest
     * @return - ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        log.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(BAD_REQUEST, "File size is loo large", ex));
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, "Persistence exception", ex));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, "Please contact Admin", ex));
    }

    /**
     * Handles Generic exception
     *
     * @param ex - Exception
     * @return - ApiError
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, ex.getMessage(), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}