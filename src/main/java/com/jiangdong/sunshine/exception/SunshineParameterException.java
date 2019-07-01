package com.jiangdong.sunshine.exception;

public class SunshineParameterException extends SunShineBaseException {

    public SunshineParameterException(){
        super();
    }

    public SunshineParameterException(String message){
        super(message);
    }

    public SunshineParameterException(String message,Exception e){
        super(message,e);
    }
}
