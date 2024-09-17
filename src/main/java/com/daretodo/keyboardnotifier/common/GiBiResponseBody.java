package com.daretodo.keyboardnotifier.common;

import com.daretodo.keyboardnotifier.common.exception.GiBiException;
import lombok.Getter;

@Getter
public class GiBiResponseBody<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> GiBiResponseBody<T> success() {
        GiBiResponseBody<T> responseBody = new GiBiResponseBody<>();
        responseBody.code = 0;
        responseBody.message = "정상 처리되었습니다.";
        return responseBody;
    }

    public static <T> GiBiResponseBody<T> success(T data) {
        GiBiResponseBody<T> responseBody = new GiBiResponseBody<>();
        responseBody.code = 0;
        responseBody.message = "정상 처리되었습니다.";
        responseBody.data(data);
        return responseBody;
    }

    @SuppressWarnings("unchecked")
    public static <T> GiBiResponseBody<T> fail(GiBiException exception) {
        GiBiResponseBody<T> responseBody = new GiBiResponseBody<>();
        responseBody.code = exception.getCode();
        responseBody.message = exception.getMessage();
        responseBody.data = (T) exception.getData();
        return responseBody;
    }

    @SuppressWarnings("unchecked")
    public GiBiResponseBody<T> data(T data) {
        this.data = data;
        return this;
    }
}
