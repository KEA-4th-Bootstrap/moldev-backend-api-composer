package org.bootstrap.apicomposer.global.common;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PageInfo(
        int pageNum,
        int pageSize,
        long totalElements,
        long totalPages
) {
}
