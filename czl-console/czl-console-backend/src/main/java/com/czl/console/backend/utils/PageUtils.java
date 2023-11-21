package com.czl.console.backend.utils;

import java.util.Collections;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/31
 * Description:
 */
public class PageUtils {

    public static <T> List<T> pageableList(List<T> data, Integer pageNum, Integer size){
        //分页从0开始
        if (null == pageNum || null == size){
            return data;
        }
        int count = data.size();
        int index = pageNum * size;
        if (index > count){
            return Collections.emptyList();
        }
        if (index+size > count){
            return data.subList(index,count);
        }else {
            return data.subList(index,index+size);
        }
    }
}
