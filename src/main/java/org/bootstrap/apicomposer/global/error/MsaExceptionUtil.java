package org.bootstrap.apicomposer.global.error;

import org.bootstrap.apicomposer.global.error.exception.BaseErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class MsaExceptionUtil {
    public static BaseErrorException Exception(HttpStatusCode errorCode) {
        if (errorCode.equals(HttpStatus.BAD_REQUEST))
            return new BaseErrorException(ErrorCode.BAD_REQUEST);
        else if (errorCode.equals(HttpStatus.UNAUTHORIZED))
            return new BaseErrorException(ErrorCode.UNAUTHORIZED);
        else if (errorCode.equals(HttpStatus.FORBIDDEN))
            return new BaseErrorException(ErrorCode.FORBIDDEN);
        else if (errorCode.equals(HttpStatus.NOT_FOUND))
            return new BaseErrorException(ErrorCode.ENTITY_NOT_FOUND);
        else if (errorCode.equals(HttpStatus.CONFLICT))
            return new BaseErrorException(ErrorCode.CONFLICT);
        else
            return new BaseErrorException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
