package com.czl.console.backend.aop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czl.console.backend.aop.domain.Logging;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogMapper extends BaseMapper<Logging> {
}
