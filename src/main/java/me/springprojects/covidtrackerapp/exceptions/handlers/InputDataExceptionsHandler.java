package me.springprojects.covidtrackerapp.exceptions.handlers;

import me.springprojects.covidtrackerapp.exceptions.InputDataException;
import me.springprojects.covidtrackerapp.exceptions.dto.InputDataExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class InputDataExceptionsHandler {

    @ExceptionHandler(value = InputDataException.class)
    public ResponseEntity<InputDataExceptionDTO> handleInvalidInputDataException(InputDataException e){
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        var exceptionDto = new InputDataExceptionDTO(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(exceptionDto);
    }
}
