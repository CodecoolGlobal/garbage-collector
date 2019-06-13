package com.codecool.garbagecollector;

public class InvalidParametersException extends Exception {

    public InvalidParametersException(Throwable error){
        super(error);
    }

    public InvalidParametersException(String errorMessage){
        super(errorMessage);
    }

    public InvalidParametersException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

}
