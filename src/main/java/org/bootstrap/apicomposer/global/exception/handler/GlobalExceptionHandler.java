package org.bootstrap.apicomposer.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public void unhandledException(Exception e, ServerHttpRequest request) {
        log.error("UnhandledException: {} {} errMessage={}\n{}",
                request.getMethod(),
                request.getURI(),
                e.getStackTrace(),
                e.getMessage()
        );
    }
}
