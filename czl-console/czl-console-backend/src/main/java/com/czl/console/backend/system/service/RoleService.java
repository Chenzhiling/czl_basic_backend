package com.czl.console.backend.system.service;

import com.czl.console.backend.security.dto.AuthorityDto;
import com.czl.console.backend.system.dto.*;
import com.czl.console.backend.system.entity.Role;
import com.czl.console.backend.system.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/5
 * Description:
 */
public interface RoleService extends BaseService<Role> {


    /**
     * 新增一个角色
     */
    Map<String,Object> addRole(AddRoleDto addRoleDto);

    /**
     * 删除一个角色
     * @param id 角色id
     */
    Map<String,Object> deleteRole(Long id);


    /**
     * 查找所有角色
     */
    Map<String,Object> queryRoles(RoleQueryCriteria criteria);


    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<AuthorityDto> mapToGrantedAuthorities(User user);


    /**
     * 根据用户id获取该id的所有角色信息
     */
    Set<RoleSmallDto> findByUserId(Long id);


    /**
     * 修改角色菜单
     */
    Map<String,Object> changeRoleMenu(RoleMenusDto dto);


    /**
     * 修改角色
     */
    Map<String,Object> updateRole(UpdateRoleDto dto);

    /**
     * 获得所有角色名称和id
     */
    Map<String,Object> queryAllRole();

    /**
     * 按需返回角色
     */
    List<Long> queryRolesBy(Long id);
}
