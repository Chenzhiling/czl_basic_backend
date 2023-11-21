package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.czl.console.backend.system.dao.RolesMenusMapper;
import com.czl.console.backend.system.entity.RolesMenus;
import com.czl.console.backend.system.service.RolesMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/15
 * Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RolesMenusServiceImpl extends BaseServiceImpl<RolesMenusMapper, RolesMenus> implements RolesMenusService {


    @Override
    public void untiedMenu(Long menuId) {
        try {
            this.remove(new LambdaQueryWrapper<RolesMenus>().eq(RolesMenus::getMenuId, menuId));
        } catch (Exception e) {
            log.error("role menu untied failed");
        }
    }


    @Override
    public void add(Long roleId, List<Long> menusIds) {
        List<RolesMenus> list = new ArrayList<>();
        for (Long menusId : menusIds) {
            RolesMenus rolesMenus = new RolesMenus();
            rolesMenus.setRoleId(roleId);
            rolesMenus.setMenuId(menusId);
            list.add(rolesMenus);
        }
        try {
            this.saveBatch(list);
            log.info("add roles_menus success");
        } catch (Exception e) {
            log.error("add roles_menus failed {}",e.getMessage());
        }
    }
}
