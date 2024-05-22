package org.bootstrap.apicomposer.global.error;

import org.bootstrap.apicomposer.global.error.exception.BaseErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class MsaExceptionUtil {
    private final static Integer STATUS_CODE_LENGTH = 3;

    public static BaseErrorException Exception(HttpStatusCode errorCode) {
//        Integer errorStatusCode = getErrorResponseCode(errorResponse);
//        System.out.println(errorStatusCode);
//        System.out.println(errorStatusCode.equals(HttpStatus.BAD_REQUEST.value()));
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

    private static Integer getErrorResponseCode(String errorResponse) {
        System.out.println(errorResponse);
        System.out.println(errorResponse.substring(0, STATUS_CODE_LENGTH));
        return Integer.parseInt(errorResponse.substring(0, STATUS_CODE_LENGTH));
    }
}
