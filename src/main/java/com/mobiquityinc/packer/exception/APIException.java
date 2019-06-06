package com.mobiquityinc.packer.exception;

/**
 * Created by Cadmy on 04.06.2019.
 */
public class APIException extends Exception {
    public APIException() {
        super();
    }

    public APIException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
