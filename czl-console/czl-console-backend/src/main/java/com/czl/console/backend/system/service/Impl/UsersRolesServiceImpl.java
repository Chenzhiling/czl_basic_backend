package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czl.console.backend.system.dao.UsersRolesMapper;
import com.czl.console.backend.system.entity.UsersRoles;
import com.czl.console.backend.system.service.UsersRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/19
 * Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UsersRolesServiceImpl extends BaseServiceImpl<UsersRolesMapper, UsersRoles> implements UsersRolesService {

    @Override
    public void add(Long userId, List<Long> roleIds) {
        List<UsersRoles> list = new ArrayList<>();
        for (Long id : roleIds) {
            UsersRoles usersRoles = new UsersRoles();
            usersRoles.setUserID(userId);
            usersRoles.setRoleId(id);
            list.add(usersRoles);
        }
        this.saveBatch(list);
    }

    @Override
    public void delete(Long userId) {
        QueryWrapper<UsersRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        this.remove(wrapper);
    }
}
