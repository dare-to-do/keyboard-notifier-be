package com.daretodo.keyboardnotifier.common;

import com.daretodo.keyboardnotifier.common.exception.SokeyException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SokeyResponseBody<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> SokeyResponseBody<T> success() {
        SokeyResponseBody<T> responseBody = new SokeyResponseBody<>();
        responseBody.code = HttpStatus.OK.value();
        responseBody.message = "정상 처리되었습니다.";
        return responseBody;
    }

    public static <T> SokeyResponseBody<T> success(T data) {
        SokeyResponseBody<T> responseBody = new SokeyResponseBody<>();
        responseBody.code = HttpStatus.OK.value();
        responseBody.message = "정상 처리되었습니다.";
        responseBody.data(data);
        return responseBody;
    }

    @SuppressWarnings("unchecked")
    public static <T> SokeyResponseBody<T> fail(SokeyException exception) {
        SokeyResponseBody<T> responseBody = new SokeyResponseBody<>();
        responseBody.code = exception.getCode();
        responseBody.message = exception.getMessage();
        responseBody.data = (T) exception.getData();
        return responseBody;
    }

    @SuppressWarnings("unchecked")
    public SokeyResponseBody<T> data(T data) {
        this.data = data;
        return this;
    }
}
