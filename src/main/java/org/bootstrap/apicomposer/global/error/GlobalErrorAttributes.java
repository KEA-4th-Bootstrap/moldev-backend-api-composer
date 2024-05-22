package org.bootstrap.apicomposer.global.error;

import org.bootstrap.apicomposer.global.error.exception.BaseErrorException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);

        Throwable throwable = getError(request);
        if (throwable instanceof BaseErrorException) {
            BaseErrorException ex = (BaseErrorException) getError(request);
            map.put("errorCode", ex.getErrorCode().getCode());
            map.put("message", ex.getErrorCode().getMessage());
        }
        return map;
    }
}
