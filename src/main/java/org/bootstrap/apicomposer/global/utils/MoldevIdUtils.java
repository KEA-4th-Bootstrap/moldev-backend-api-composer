package org.bootstrap.apicomposer.global.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MoldevIdUtils {
    public static <T extends MoldevIdField> List<String> getMoldevIds(List<T> responseDtoList) {
        return responseDtoList.stream()
                .map(MoldevIdField::getMoldevId)
                .collect(Collectors.toList());
    }
}
