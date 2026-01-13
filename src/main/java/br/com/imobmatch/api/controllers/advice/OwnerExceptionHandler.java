package br.com.imobmatch.api.controllers.advice;

import br.com.imobmatch.api.dtos.error.ErrorResponseDTO;
import br.com.imobmatch.api.exceptions.owner.InappropriateUserRoleException;
import br.com.imobmatch.api.exceptions.owner.NoValidDataProvideException;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.exceptions.owner.OwnerNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice(basePackages = "br.com.imobmatch.api.controllers")
public class OwnerExceptionHandler {
    @ExceptionHandler(InappropriateUserRoleException.class)
    public ResponseEntity<ErrorResponseDTO> handleInappropriateUserRole(

            InappropriateUserRoleException ex,
            HttpServletRequest request
    ){

        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()


        );

        return  ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(error);
    }

    @ExceptionHandler(OwnerExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleOwnerExists(
            OwnerExistsException ex,
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

    @ExceptionHandler(NoValidDataProvideException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoValidDataProvide(
            NoValidDataProvideException ex,
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

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleOwnerNotExists(
            OwnerNotFoundException ex,
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
