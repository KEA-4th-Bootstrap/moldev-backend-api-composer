package org.bootstrap.apicomposer.global.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MemberIdUtils {
    public static <T extends MemberIdField> List<Long> getMemberIds(List<T> responseDtoList) {
        return responseDtoList.stream()
                .map(MemberIdField::getMemberId)
                .collect(Collectors.toList());
    }
}
