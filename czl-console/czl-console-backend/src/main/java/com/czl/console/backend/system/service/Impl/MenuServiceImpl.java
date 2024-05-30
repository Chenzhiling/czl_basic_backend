package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.security.utils.SecurityUtils;
import com.czl.console.backend.system.dao.MenuMapper;
import com.czl.console.backend.system.dto.MenuDto;
import com.czl.console.backend.system.dto.RoleSmallDto;
import com.czl.console.backend.system.entity.Menu;
import com.czl.console.backend.system.service.MenuService;
import com.czl.console.backend.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu>
        implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleService roleService;


    public MenuServiceImpl(MenuMapper menuMapper,
                           RoleService roleService) {
        this.menuMapper = menuMapper;
        this.roleService = roleService;
    }

    @Override
    public Map<String, Object> buildTree() {
        try {
            List<Menu> menus = this.list();
            List<MenuDto> parentMenuList = new ArrayList<>();
            //一级菜单
            menus.stream().filter(menu -> menu.getParentId() == 0).forEach(menu -> {
                MenuDto menuDto = new MenuDto();
                try {
                    BeanUtils.copyProperties(menuDto, menu);
                    parentMenuList.add(menuDto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            List<MenuDto> collect = parentMenuList.stream().peek(s ->
                    s.setChildren(getChildren(s, menus))).
                    sorted(Comparator.comparingInt(MenuDto::getSort)).
                    collect(Collectors.toList());
            return RestResponse.success(collect).put("total", collect.size());
        } catch (Exception e) {
            log.error("build menu tree failed: ", e);
            return RestResponse.fail("构建菜单树失败: " + e.getMessage());
        }
    }

    private List<MenuDto> getChildren(MenuDto s, List<Menu> menus) {
        List<MenuDto> menuDtoList = new ArrayList<>();
        if (!ObjectUtils.isNull(menus) && menus.size() != 0) {
            for (Menu m : menus) {
                if (s.getId().equals(m.getParentId())) {
                    MenuDto menuDto = new MenuDto();
                    try {
                        BeanUtils.copyProperties(menuDto, m);
                        menuDtoList.add(menuDto);
                        menuDto.setChildren(getChildren(menuDto, menus));
                    } catch (Exception e) {
                        log.error("build sub menu tree failed: ", e);
                    }
                }
            }
        }
        return menuDtoList;
    }


    @Override
    public Map<String, Object> listALl() {
        //获取所有menu
        Set<RoleSmallDto> roles = roleService.findByUserId(SecurityUtils.getCurrentUserId());
        List<Menu> menus = this.findByRoles(roles);
        return RestResponse.success(menus);
    }


    @Override
    public List<Menu> findByRoles(Set<RoleSmallDto> roles) {
        List<Long> ids = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toList());
        return menuMapper.findMenusByRoleIds(ids);
    }
}
