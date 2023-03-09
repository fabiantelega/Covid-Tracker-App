package me.springprojects.covidtrackerapp.exceptions;

import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;

public abstract class ServiceException extends RuntimeException{

    public ServiceException(ServiceExceptions message){
        super(message.toString());
    }
}
