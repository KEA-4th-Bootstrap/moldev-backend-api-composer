package org.bootstrap.apicomposer.domain.report.util;

public record PageInfo(
        int pageNum,
        int pageSize,
        long totalElements,
        long totalPages
) {
}
