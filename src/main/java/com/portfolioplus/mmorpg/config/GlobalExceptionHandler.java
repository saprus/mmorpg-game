package com.portfolioplus.mmorpg.config;

import com.portfolioplus.mmorpg.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorDTO> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        ApiErrorDTO body = new ApiErrorDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getReason(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }


    // Catch for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleAll(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiErrorDTO body = new ApiErrorDTO(
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(500)
                .body(body);
    }
}
