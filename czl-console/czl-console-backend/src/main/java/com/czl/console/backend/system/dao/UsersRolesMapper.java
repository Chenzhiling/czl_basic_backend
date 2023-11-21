package com.czl.console.backend.system.dao;

import com.czl.console.backend.system.entity.UsersRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/19
 * Description:
 */
@Mapper
@Repository
public interface UsersRolesMapper extends CoreMapper<UsersRoles> {
}
