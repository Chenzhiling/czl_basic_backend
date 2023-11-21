package com.czl.console.backend.system.service;

import com.czl.console.backend.system.entity.RolesMenus;

import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/15
 * Description:
 */
public interface RolesMenusService extends BaseService<RolesMenus> {


    /**
     * 解绑RoleMenus
     */
    void untiedMenu(Long menuId);

    /**
     * 新增RoleMenus
     */
    void add(Long roleId, List<Long> menusIds);
}
