package br.com.imobmatch.api.controllers.advice;

import br.com.imobmatch.api.dtos.error.ErrorResponseDTO;
import br.com.imobmatch.api.exceptions.property.PropertyIllegalBusinessPriceValueException;
import br.com.imobmatch.api.exceptions.property.PropertyNotFoundException;
import br.com.imobmatch.api.exceptions.property.PropertyNotUploadImagen;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice(basePackages = "br.com.imobmatch.api.controllers")
public class PropertyExceptionHandler {

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePropertyNotFound(
            PropertyNotFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(PropertyIllegalBusinessPriceValueException.class)
    public ResponseEntity<ErrorResponseDTO> handlePropertyIllegalBusinessPriceValue(
            PropertyIllegalBusinessPriceValueException ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(PropertyNotUploadImagen.class)
    public ResponseEntity<ErrorResponseDTO> handlePropertyImageNotFound(
            PropertyNotFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}