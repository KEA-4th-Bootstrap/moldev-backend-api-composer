package org.bootstrap.apicomposer.global.error.exception;

import lombok.Getter;
import org.bootstrap.apicomposer.global.error.ErrorCode;

@Getter
public class BaseErrorException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
