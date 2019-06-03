package com.jiangdong.sunshine.exception;

public class SunshineSQLException extends SunShineBaseException {

    public SunshineSQLException(){

    }

    public SunshineSQLException(String message){
        super(message);
    }

    public SunshineSQLException(String message, Throwable cause){
        super(message,cause);
    }

}
