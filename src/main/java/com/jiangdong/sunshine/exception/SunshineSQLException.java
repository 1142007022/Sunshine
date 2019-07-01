package com.jiangdong.sunshine.exception;

public class SunshineSQLException extends SunShineBaseException {

    public SunshineSQLException(){
        super();
    }

    public SunshineSQLException(String message){
        super(message);
    }

    public SunshineSQLException(String message, Throwable cause){
        super(message,cause);
    }

}
