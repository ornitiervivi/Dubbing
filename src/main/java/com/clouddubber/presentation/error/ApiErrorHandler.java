package com.clouddubber.presentation.error;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse bad(IllegalArgumentException ex){ return new ErrorResponse("BAD_REQUEST", ex.getMessage(), List.of(), Instant.now().toString(), "n/a"); }
    @ExceptionHandler(Exception.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(Exception ex){ return new ErrorResponse("NOT_FOUND", ex.getMessage(), List.of(), Instant.now().toString(), "n/a"); }
    public static class ErrorResponse {
        public String code; public String message; public List<String> details; public String timestamp; public String traceId;
        public ErrorResponse(String code, String message, List<String> details, String timestamp, String traceId){this.code=code;this.message=message;this.details=details;this.timestamp=timestamp;this.traceId=traceId;}
    }
}
