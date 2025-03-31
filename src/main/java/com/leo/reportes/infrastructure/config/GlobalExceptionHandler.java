package com.leo.reportes.infrastructure.config;


import com.leo.reportes.infrastructure.exceptions.UserNotFoundException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse> badRequestHandler(BadRequestException bre) {
        LOGGER.error("badRequestHandler - message {}", bre.getMessage());
        CustomResponse cr = new CustomResponse((HttpStatus.BAD_REQUEST), bre.getMessage());
        return new ResponseEntity<>(cr, cr.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomResponse> notFoundExceptionHandler(NotFoundException nfe) {
        LOGGER.error("notFoundExceptionHandler - message: {}", nfe.getMessage());
        CustomResponse cr = new CustomResponse((HttpStatus.NOT_FOUND), nfe.getMessage());
        return new ResponseEntity<>(cr, cr.getStatus());
    }*/


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomResponse> unauthorizedExceptionHandler(UserNotFoundException ue) {
        LOGGER.error(ue.getMessage());
        CustomResponse cr = new CustomResponse((HttpStatus.NOT_FOUND), ue.getMessage());
        return new ResponseEntity<>(cr, cr.getStatus());
    }

    @Getter
    public static class CustomResponse {

        private final LocalDateTime date;

        private final HttpStatus status;

        private final String message;
        public CustomResponse(HttpStatus status, String message) {
            this.date = LocalDateTime.now();
            this.status = status;
            this.message = message;
        }

    }
}
