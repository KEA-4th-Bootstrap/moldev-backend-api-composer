package org.bootstrap.apicomposer.global.exception.custom;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException(String message) {
        super(message);
    }
}
