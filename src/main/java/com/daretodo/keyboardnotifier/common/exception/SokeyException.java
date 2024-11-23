package com.daretodo.keyboardnotifier.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SokeyException extends RuntimeException {

    private Integer code;
    @Setter
    private Object data;

    public SokeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SokeyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SokeyException(String message) {
        super(message);
    }

}
