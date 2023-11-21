package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.base.dozer.Convertor;
import com.czl.console.backend.security.dto.AuthorityDto;
import com.czl.console.backend.security.utils.SecurityUtils;
import com.czl.console.backend.system.dao.MenuMapper;
import com.czl.console.backend.system.dao.RoleMapper;
import com.czl.console.backend.system.dao.RolesMenusMapper;
import com.czl.console.backend.system.dto.*;
import com.czl.console.backend.system.entity.Menu;
import com.czl.console.backend.system.entity.Role;
import com.czl.console.backend.system.entity.RolesMenus;
import com.czl.console.backend.system.entity.User;
import com.czl.console.backend.system.service.RoleService;
import com.czl.console.backend.system.service.RolesMenusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/5
 * Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role>
        implements RoleService {

    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;

    private final RolesMenusService rolesMenusService;

    private final RolesMenusMapper rolesMenusMapper;

    private final Convertor convertor;


    public RoleServiceImpl(RoleMapper roleMapper,
                           MenuMapper menuMapper,
                           RolesMenusService rolesMenusService,
                           RolesMenusMapper rolesMenusMapper,
                           Convertor convertor) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.rolesMenusService = rolesMenusService;
        this.rolesMenusMapper = rolesMenusMapper;
        this.convertor = convertor;
    }


    @Override
    public Map<String, Object> addRole(AddRoleDto addRoleDto) {
        Integer count = this.lambdaQuery().eq(Role::getName, addRoleDto.getName())
            .eq(Role::getIsDelete, false).count();
        if (count > 0) {
            return RestResponse.fail("Duplicate role name for " + addRoleDto.getName());
        }
        Role role = convertor.convert(addRoleDto, Role.class);
        try {
            this.save(role);
            Long roleId = role.getId();
            rolesMenusService.add(roleId,addRoleDto.getMenusId());
            log.info("{} add role success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error("add role failed {}",e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> deleteRole(Long id) {
        try {
            rolesMenusService.lambdaUpdate().eq(RolesMenus::getRoleId, id).remove();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail("please delete sys_roles_menus data first");
        }
        try {
            this.removeById(id);
            log.info("{} delete role success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> queryRoles(RoleQueryCriteria criteria) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", false);
        Page<Role> page = new Page<>(criteria.getCurrent(), criteria.getSize());
        try {
            Page<Role> rolePage = this.page(page, wrapper);
            List<Role> roles = rolePage.getRecords();
            for (Role role : roles) {
                role.setMenus(menuMapper.findMenusByRoleId(role.getId()));
            }
            return RestResponse.success(roles).put("total", rolePage.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }


    @Override
    public List<AuthorityDto> mapToGrantedAuthorities(User user) {
        Set<String> permissions = new HashSet<>();
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(AuthorityDto::new)
                    .collect(Collectors.toList());
        }
        Set<Role> roles = roleMapper.findByUserId(user.getId());
        for (Role role : roles) {
            Set<Menu> menus = menuMapper.findMenusByRoleId(role.getId());
            role.setMenus(menus);
        }

        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                .map(Menu::getPermission)
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        return permissions.stream().map(AuthorityDto::new)
                .collect(Collectors.toList());
    }


    @Override
    public Set<RoleSmallDto> findByUserId(Long id) {
        Set<Role> roles = roleMapper.findByUserId(id);
        return convertor.convert(roles, RoleSmallDto.class);
    }


    @Override
    public Map<String, Object> changeRoleMenu(RoleMenusDto dto) {
        Set<Long> menus = dto.getMenus();
        if (menus.size() > 0) {
            List<RolesMenus> rolesMenusList = menus.stream().map(i -> {
                RolesMenus rolesMenus = new RolesMenus();
                rolesMenus.setRoleId(dto.getId());
                rolesMenus.setMenuId(i);
                return rolesMenus;
            }).collect(Collectors.toList());
            //删除rolesMenus表旧信息
            try {
                rolesMenusService.remove(new LambdaQueryWrapper<RolesMenus>().eq(RolesMenus::getRoleId, dto.getId()));
                //保存新信息
                rolesMenusService.saveBatch(rolesMenusList);
                log.info("{} change role_menu success",SecurityUtils.getCurrentUsername());
                return RestResponse.success();
            } catch (Exception e) {
                log.error(e.getMessage());
                return RestResponse.fail("change role menu failed");
            }
        } else {
            //如果menus为空，则清空对应的角色菜单信息
            try {
                rolesMenusService.remove(new LambdaQueryWrapper<RolesMenus>().eq(RolesMenus::getRoleId, dto.getId()));
                log.info("{} change role_menu success",SecurityUtils.getCurrentUsername());
                return RestResponse.success();
            } catch (Exception e) {
                log.error(e.getMessage());
                return RestResponse.fail("change role menu failed");
            }
        }
    }

    @Override
    public Map<String, Object> updateRole(UpdateRoleDto dto) {
        Role role = convertor.convert(dto, Role.class);
        try {
            this.updateById(role);
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> queryAllRole() {
        try {
            List<RoleNameVo> vos = roleMapper.queryAllRole();
            return RestResponse.success(vos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public List<Long> queryRolesBy(Long id) {
        QueryWrapper<RolesMenus> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        try {
            List<RolesMenus> rolesMenusList = rolesMenusMapper.selectList(wrapper);
            return rolesMenusList.stream().map(RolesMenus::getMenuId).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("query sys_roles_menus failed {}",e.getMessage());
            return Collections.emptyList();
        }
    }
}
