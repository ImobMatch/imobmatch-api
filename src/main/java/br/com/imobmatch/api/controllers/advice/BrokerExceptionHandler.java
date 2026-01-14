package br.com.imobmatch.api.controllers.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.imobmatch.api.dtos.error.ErrorResponseDTO;
import br.com.imobmatch.api.exceptions.broker.BrokerExistsException;
import br.com.imobmatch.api.exceptions.broker.BrokerNoValidDataProvideException;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@Hidden
@RestControllerAdvice(basePackages = "br.com.imobmatch.api.controllers")
public class BrokerExceptionHandler {
    
    @ExceptionHandler(BrokerExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBrokerExists(
            BrokerExistsException ex,
            HttpServletRequest request
    ){
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()

        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(BrokerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBrokerNotExists(
            BrokerNotFoundException ex,
            HttpServletRequest request
    )
    {
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

    
    @ExceptionHandler(BrokerNoValidDataProvideException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoValidDataProvide(
            BrokerNoValidDataProvideException ex,
            HttpServletRequest request
    )
    {
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
}