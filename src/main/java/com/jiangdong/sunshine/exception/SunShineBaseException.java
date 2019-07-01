package com.jiangdong.sunshine.exception;

public class SunShineBaseException extends RuntimeException {

    public SunShineBaseException(){
        super();
    }

    public SunShineBaseException(String message){
        super(message);
    }

    public SunShineBaseException(String message, Throwable cause){
        super(message,cause);
    }

}
