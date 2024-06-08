package org.bootstrap.apicomposer.domain.report.dto;

import org.bootstrap.apicomposer.domain.report.util.PageInfo;
import org.bootstrap.apicomposer.domain.report.vo.ReportResponseVo;

import java.util.List;

public record ReportResponseDto (
        List<ReportResponseVo> reportResponseVo,
        PageInfo pageInfo
) {
}
