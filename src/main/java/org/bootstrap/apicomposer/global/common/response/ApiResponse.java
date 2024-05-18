package org.bootstrap.apicomposer.global.common.response;

import lombok.Builder;
import lombok.Getter;

@Builder(access = lombok.AccessLevel.PRIVATE)
@Getter
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<?> of(ResponseCode responseCode, T data) {
        return ApiResponse.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }
}
