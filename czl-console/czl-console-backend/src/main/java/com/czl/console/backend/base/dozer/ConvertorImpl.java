package com.czl.console.backend.base.dozer;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/5/5
 * Description: dozer转换器
 */
@Component
public class ConvertorImpl implements Convertor{

    @Autowired
    private Mapper dozerMapper;


    @Override
    public <T, S> List<T> convert(List<S> source, Class<T> clz) {
        if (source == null) return null;
        List<T> map = new ArrayList<>();
        for (S s : source) {
            map.add(dozerMapper.map(s, clz));
        }
        return map;
    }

    @Override
    public <T, S> T convert(S source, Class<T> clz) {
        return source == null ? null : this.dozerMapper.map(source, clz);
    }

    @Override
    public <T, S> Set<T> convert(Set<S> source, Class<T> clz) {
        if (source == null) return null;

        Set<T> set = new HashSet<>();
        for (S s : source) {
            set.add(dozerMapper.map(s, clz));
        }
        return set;
    }

    @Override
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        return arr;
    }
}
