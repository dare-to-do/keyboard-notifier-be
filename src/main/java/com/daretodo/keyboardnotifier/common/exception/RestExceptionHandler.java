package com.daretodo.keyboardnotifier.common.exception;

import com.daretodo.keyboardnotifier.common.GiBiResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GiBiException.class)
    public ResponseEntity<GiBiResponseBody> handleDeliveryAreaException(GiBiException exception) {
        return ResponseEntity.ok().body(GiBiResponseBody.fail(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GiBiResponseBody> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok().body(GiBiResponseBody.fail(new GiBiException(exception.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return ResponseEntity.ok().body(GiBiResponseBody.fail(new GiBiException(exception.getMessage())));
    }
}
