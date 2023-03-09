package me.springprojects.covidtrackerapp.exceptions.handlers;

import me.springprojects.covidtrackerapp.exceptions.ServiceException;
import me.springprojects.covidtrackerapp.exceptions.dto.ServiceExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ServiceExceptionsHandler {

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ServiceExceptionDTO> handleServiceExceptions(ServiceException e){
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var exceptionDto = new ServiceExceptionDTO(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(exceptionDto);
    }
}
