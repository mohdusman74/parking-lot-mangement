package com.example.parking.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(NoSpotAvailableException.class)
    public ResponseEntity<ApiError> noSpot(NoSpotAvailableException e) {
        return build(HttpStatus.CONFLICT, e.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> conflict(IllegalStateException e) {
        return build(HttpStatus.CONFLICT, e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
            .map(x -> x.getField() + ": " + x.getDefaultMessage()).findFirst()
            .orElse("Request validation failed");
        return build(HttpStatus.BAD_REQUEST, msg);
    }
    private ResponseEntity<ApiError> build(HttpStatus s, String msg) {
        return ResponseEntity.status(s).body(new ApiError(LocalDateTime.now(), s.value(), s.getReasonPhrase(), msg));
    }
}
