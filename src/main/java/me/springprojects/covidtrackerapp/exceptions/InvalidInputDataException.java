package me.springprojects.covidtrackerapp.exceptions;

import me.springprojects.covidtrackerapp.exceptions.enums.UserExceptions;

public class InvalidInputDataException extends InputDataException{

    public InvalidInputDataException(UserExceptions message){
        super(message);
    }
}
