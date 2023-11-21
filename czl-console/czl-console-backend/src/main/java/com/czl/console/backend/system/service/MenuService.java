package com.czl.console.backend.system.service;

import com.czl.console.backend.system.dto.RoleSmallDto;
import com.czl.console.backend.system.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MenuService extends BaseService<Menu> {


    /**
     * 新增一条数据
     */
    Map<String, Object> addMenu(Menu menu);


    /**
     * 删除一条数据
     */
    Map<String, Object> deleteMenu(Long id);


    /**
     * 根据pid查找所有菜单
     */
    Map<String, Object> findMenusByPid(Long parentId);


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

    Map<String, Object> listMenus();
}
