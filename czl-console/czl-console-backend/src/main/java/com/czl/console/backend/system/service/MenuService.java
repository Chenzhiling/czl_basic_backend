package com.czl.console.backend.system.service;

import com.czl.console.backend.system.dto.RoleSmallDto;
import com.czl.console.backend.system.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MenuService extends BaseService<Menu> {



    /**
     * 构建菜单树，所有菜单
     */
    Map<String, Object> buildTree();


    /**
     * 根据所有角色id集合找到所有菜单
     */
    List<Menu> findByRoles(Set<RoleSmallDto> roles);


    /**
     * 构建用户的菜单树
     */
    Map<String, Object> listALl();
}
