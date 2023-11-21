package com.czl.console.backend.system.dao;

import com.czl.console.backend.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/4
 * Description:
 */
@Mapper
@Repository
public interface UserMapper extends CoreMapper<User> {


    @Select("select email from sys_user where user_name = 'admin' and is_delete = false")
    String getAdminEmail();
}
