package com.czl.console.backend.system.dao;

import com.czl.console.backend.system.dto.RoleNameVo;
import com.czl.console.backend.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/5
 * Description:
 */
@Mapper
@Repository
public interface RoleMapper extends CoreMapper<Role> {


    /**
     * 根据用户id连表查询role
     * @param id user id
     * @return role数组
     */
    @Select("select r.* from sys_role r, sys_users_roles ur where r.id = ur.role_id and ur.user_id = #{id}")
    Set<Role> findByUserId(@Param("id") Long id);

    @Select("select id, name from sys_role where is_delete = false")
    List<RoleNameVo> queryAllRole();

}
