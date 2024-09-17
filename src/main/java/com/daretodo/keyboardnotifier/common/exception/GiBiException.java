package com.daretodo.keyboardnotifier.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GiBiException extends RuntimeException {

    private Integer code;
    @Setter
    private Object data;

    public GiBiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiBiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public GiBiException(String message) {
        super(message);
    }

}
