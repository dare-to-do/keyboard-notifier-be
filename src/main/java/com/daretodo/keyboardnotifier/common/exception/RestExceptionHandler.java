package com.daretodo.keyboardnotifier.common.exception;

import com.daretodo.keyboardnotifier.common.SokeyResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SokeyException.class)
    public ResponseEntity<SokeyResponseBody> handleDeliveryAreaException(SokeyException exception) {
        return ResponseEntity.ok().body(SokeyResponseBody.fail(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SokeyResponseBody> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.ok().body(SokeyResponseBody.fail(new SokeyException(exception.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return ResponseEntity.ok().body(SokeyResponseBody.fail(new SokeyException(exception.getMessage())));
    }
}
