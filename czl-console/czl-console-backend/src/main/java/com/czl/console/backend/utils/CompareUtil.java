package com.czl.console.backend.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/5/17
 * Description:
 */
public class CompareUtil {

    /**
     * 按value值将序排列
     */
    public static <K extends Comparable<K>, V extends Comparable<V>> Map<K, V> sortMapByValues(Map<K, V> aMap) {
        HashMap<K, V> finalOut = new LinkedHashMap<>();
        aMap.entrySet()
                .stream()
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
                .collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
        return finalOut;
    }
}


