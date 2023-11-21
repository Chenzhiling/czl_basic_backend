package com.czl.console.backend.base.dozer;

import java.util.List;
import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/5/5
 * Description: dozer转换器
 */
public interface Convertor {


     <T, S> List<T> convert(List<S> source, Class<T> clz);


     <T, S> T convert(S source,Class<T> clz);


     <T,S> Set<T> convert(Set<S> source,Class<T> clz);


     <T, S> T[] convert(S[] s, Class<T> clz);
}
