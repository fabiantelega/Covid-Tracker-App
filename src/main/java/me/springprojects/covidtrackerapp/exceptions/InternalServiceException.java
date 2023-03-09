package me.springprojects.covidtrackerapp.exceptions;

import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;

public class InternalServiceException extends ServiceException{

    public InternalServiceException(ServiceExceptions message){
        super(message);
    }
}
