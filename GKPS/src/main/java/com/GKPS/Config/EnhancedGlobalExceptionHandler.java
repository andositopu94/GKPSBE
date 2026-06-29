package com.GKPS.Config;


import com.GKPS.DTO.Response.ApiResponse;
import com.GKPS.DTO.Response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class EnhancedGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(EnhancedGlobalExceptionHandler.class);

    //handle custom ApiException
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException ex, HttpServletRequest request) {
        String traceID = UUID.randomUUID().toString();
        logger.warn("[{}] API Exception: {}", traceID, ex.getMessage(), ex.getErrorCode());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .traceId(traceID)
                .build();

        ApiResponse<?> response = ApiResponse.error(ex.getMessage(), errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    //Exception Resources Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return handleApiException(ex, request);
    }

    //handle duplicate resource exception
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request) {
        return handleApiException(ex, request);
    }

    //handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String traceID = UUID.randomUUID().toString();
        logger.warn("[{}] Validation Error occurred: {}", traceID);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("VALIDATION_ERROR")
                .message("Validation failed for one or more fields.")
                .traceId(traceID)
                .build();

        ApiResponse<?> response = ApiResponse.error("Validation failed for one or more fields.", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //handle bad credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        String traceID = UUID.randomUUID().toString();
        logger.warn("[{}] Bad Credentials: {}", traceID, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of("INVALID_CREDENTIALS", "Invalid username or password.", Map.of("Hint", "Please check your username and password and try again."));
                errorResponse.setTraceId(traceID);

        ApiResponse<?> response = ApiResponse.error("Authentication Failed", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle authentication exceptions
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        logger.warn("[{}] Authentication error: {}", traceId, ex.getMessage());

        String message = ex.getMessage() != null && ex.getMessage().contains("Bad credentials")
                ? "Username atau password salah"
                : "Autentikasi gagal";

        ErrorResponse errorResponse = ErrorResponse.of(
                "AUTHENTICATION_FAILED",
                message
        );
        errorResponse.setTraceId(traceId);

        ApiResponse<?> response = ApiResponse.error(message, errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle type mismatch (e.g., invalid date format)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        logger.warn("[{}] Type mismatch: {}", traceId, ex.getMessage());

        String message = String.format(
                "Parameter '%s' harus bertipe %s, diterima: %s",
                ex.getName(),
                ex.getRequiredType().getSimpleName(),
                ex.getValue()
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("INVALID_PARAMETER_TYPE")
                .message(message)
                .traceId(traceId)
                .build();

        ApiResponse<?> response = ApiResponse.error("Parameter tidak valid", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle 404 Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(
            NoHandlerFoundException ex,
            HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        logger.warn("[{}] Endpoint tidak ditemukan: {} {}", traceId, ex.getHttpMethod(), ex.getRequestURL());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("ENDPOINT_NOT_FOUND")
                .message(String.format("Endpoint %s %s tidak ditemukan", ex.getHttpMethod(), ex.getRequestURL()))
                .traceId(traceId)
                .build();

        ApiResponse<?> response = ApiResponse.error("Endpoint tidak ditemukan", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle all other runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        logger.error("[{}] Runtime exception occurred", traceId, ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("INTERNAL_ERROR")
                .message("Terjadi kesalahan. Silakan hubungi support.")
                .traceId(traceId)
                .build();

        ApiResponse<?> response = ApiResponse.error("Kesalahan internal server", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {
        String traceId = UUID.randomUUID().toString();
        logger.error("[{}] Unhandled exception occurred", traceId, ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("UNEXPECTED_ERROR")
                .message("Terjadi kesalahan yang tidak terduga.")
                .traceId(traceId)
                .build();

        ApiResponse<?> response = ApiResponse.error("Kesalahan internal server", errorResponse);
        response.setPath(request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
