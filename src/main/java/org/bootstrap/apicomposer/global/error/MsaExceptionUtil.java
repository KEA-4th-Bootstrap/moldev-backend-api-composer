package org.bootstrap.apicomposer.global.error;

import org.bootstrap.apicomposer.global.error.exception.BaseErrorException;
import org.springframework.http.HttpStatus;

public class MsaExceptionUtil {
    private final static Integer STATUS_CODE_LENGTH = 3;

    public static BaseErrorException Exception(String errorResponse) {
        Integer errorStatusCode = getErrorResponseCode(errorResponse);
        System.out.println(errorStatusCode);
        System.out.println(errorStatusCode.equals(HttpStatus.BAD_REQUEST.value()));
        if (errorStatusCode.equals(HttpStatus.BAD_REQUEST.value()))
            return new BaseErrorException(ErrorCode.BAD_REQUEST);
        else if (errorStatusCode.equals(HttpStatus.UNAUTHORIZED.value()))
            return new BaseErrorException(ErrorCode.UNAUTHORIZED);
        else if (errorStatusCode.equals(HttpStatus.FORBIDDEN.value()))
            return new BaseErrorException(ErrorCode.FORBIDDEN);
        else if (errorStatusCode.equals(HttpStatus.NOT_FOUND.value()))
            return new BaseErrorException(ErrorCode.ENTITY_NOT_FOUND);
        else if (errorStatusCode.equals(HttpStatus.CONFLICT.value()))
            return new BaseErrorException(ErrorCode.CONFLICT);
        else
            return new BaseErrorException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private static Integer getErrorResponseCode(String errorResponse) {
        return Integer.parseInt(errorResponse.substring(STATUS_CODE_LENGTH));
    }
}
