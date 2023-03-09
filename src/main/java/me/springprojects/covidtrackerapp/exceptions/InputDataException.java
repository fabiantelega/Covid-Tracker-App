package me.springprojects.covidtrackerapp.exceptions;

import me.springprojects.covidtrackerapp.exceptions.enums.UserExceptions;

public abstract class InputDataException extends RuntimeException{

    public InputDataException(UserExceptions message){
        super(message.toString());
    }
}
