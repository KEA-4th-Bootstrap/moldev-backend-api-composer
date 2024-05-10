package org.bootstrap.apicomposer.domain.test.dto.res;

import lombok.Builder;

@Builder
public record GetResponseDto(
        String str1,
        String str2,
        String str3
) {

    public static GetResponseDto of(String str1, String str2, String str3) {
        return GetResponseDto.builder()
                .str1(str1)
                .str2(str2)
                .str3(str3)
                .build();
    }
}
