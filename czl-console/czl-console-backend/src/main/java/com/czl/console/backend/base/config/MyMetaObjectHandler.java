package com.czl.console.backend.base.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/4/29
 * Description: mybatis-plus自动注入
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Timestamp(System.currentTimeMillis()),metaObject);
        this.setFieldValByName("updateTime",new Timestamp(System.currentTimeMillis()),metaObject);
        this.setFieldValByName("isDelete",false,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Timestamp(System.currentTimeMillis()),metaObject);
    }
}
