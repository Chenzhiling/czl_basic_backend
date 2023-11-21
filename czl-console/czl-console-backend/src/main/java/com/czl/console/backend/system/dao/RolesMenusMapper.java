package com.czl.console.backend.system.dao;

import com.czl.console.backend.system.entity.RolesMenus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolesMenusMapper extends CoreMapper<RolesMenus> {
}
