package com.czl.console.backend.system.service;

import com.czl.console.backend.system.entity.UsersRoles;

import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/19
 * Description:
 */
public interface UsersRolesService extends BaseService<UsersRoles> {

    void add(Long userId, List<Long> roleIds);

    void delete(Long userId);
}
