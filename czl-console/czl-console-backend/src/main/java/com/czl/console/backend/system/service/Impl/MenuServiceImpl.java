package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.base.dozer.Convertor;
import com.czl.console.backend.security.utils.SecurityUtils;
import com.czl.console.backend.system.dao.MenuMapper;
import com.czl.console.backend.system.dto.MenuDto;
import com.czl.console.backend.system.dto.MenuRouters;
import com.czl.console.backend.system.dto.RoleSmallDto;
import com.czl.console.backend.system.dto.RouterDTO;
import com.czl.console.backend.system.entity.Menu;
import com.czl.console.backend.system.service.MenuService;
import com.czl.console.backend.system.service.RoleService;
import com.czl.console.backend.system.service.RolesMenusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu>
        implements MenuService{

    private final MenuMapper menuMapper;

    private final Convertor convertor;

    private final RoleService roleService;

    private final RolesMenusService rolesMenusService;

    public MenuServiceImpl(MenuMapper menuMapper,
                           Convertor convertor,
                           RoleService roleService,
                           RolesMenusService rolesMenusService) {
        this.menuMapper = menuMapper;
        this.convertor = convertor;
        this.roleService = roleService;
        this.rolesMenusService = rolesMenusService;
    }

    @Override
    public Map<String, Object> addMenu(Menu menu) {
        if (!ObjectUtils.isEmpty(this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getTitle, menu.getTitle())))) {
            return RestResponse.fail("duplicate title: " + menu.getTitle());
        }
        if (!ObjectUtils.isEmpty(this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getName, menu.getName())))) {
            return RestResponse.fail("duplicate name: " + menu.getName());
        }
        if (menu.getPid().equals(0L)) {
            menu.setPid(null);
        }
        try {
            this.save(menu);
            log.info("{} save menu success",SecurityUtils.getCurrentUsername());
            updateSubCnt(menu.getPid());
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> deleteMenu(Long id) {
        Set<Menu> menuDeletes = new HashSet<>();
        Menu menu = this.getById(id);
        if (ObjectUtils.isNull(menu)) {
            return RestResponse.fail("menu is null");
        }
        List<Menu> children = getMenus(id);
        getChildMenus(children, menuDeletes);
        menuDeletes.add(menu);

        try {
            for (Menu menuDelete : menuDeletes) {
                this.removeById(menuDelete.getId());
                rolesMenusService.untiedMenu(menuDelete.getId());
                if (!ObjectUtils.isNull(menu.getPid())) {
                    updateSubCnt(menu.getPid());
                }
            }
            log.info("{} delete menu success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> findMenusByPid(Long parentId) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if (parentId.equals(0L)) {
            wrapper.isNull("pid");
        } else {
            wrapper.eq("pid", parentId);
        }
        List<Menu> menus = menuMapper.selectList(wrapper);
        return RestResponse.success(menus).put("total", menus.size());
    }


    @Override
    public Map<String, Object> buildTree() {
        try {
            List<Menu> menus = this.list();
            for (Menu menu : menus) {
                menu.setTitle(menu.getName());
            }
            List<MenuDto> menuDtoS = buildTree(menus);
            return RestResponse.success(menuDtoS).put("total", menuDtoS.size());
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> listALl() {
        //获取所有menu
        Set<RoleSmallDto> roles = roleService.findByUserId(SecurityUtils.getCurrentUserId());
        List<Menu> menus = this.findByRoles(roles);

        List<RouterDTO> list = new ArrayList<>();

        menus.stream().filter(menu -> menu.getPid() == null).forEach(menu -> {
            RouterDTO router = new RouterDTO();
            router.setRouter(menu.getTitle());
            router.setName(menu.getName());
            router.setIcon(menu.getIcon());
            if (menu.getSubCount() == 0) {
                router.setPath(menu.getPath());
            }
            router.setId(menu.getId());
            router.setPid(menu.getPid());
            router.setMenuSort(menu.getMenuSort());
            list.add(router);
        });

        List<RouterDTO> collect = list.stream().peek(s -> {
            if (s.getPath() == null)
                s.setChildren(getChildren(s, menus));
        }).sorted(Comparator.comparingInt(menu -> (menu.getMenuSort() == null ? 0 : menu.getMenuSort()))).collect(Collectors.toList());
        return RestResponse.success(collect);
    }

    @Override
    public Map<String, Object> listMenus() {
        //获取所有menu
        Set<RoleSmallDto> roles = roleService.findByUserId(SecurityUtils.getCurrentUserId());
        List<Menu> menus = this.findByRoles(roles);
        List<MenuRouters> menuRouters = new ArrayList<>();
        menus.stream().forEach(s -> {
            MenuRouters router = new MenuRouters();
            router.setPath(s.getTitle());
            router.setComponent(s.getComponent());
            router.setIcon(s.getIcon());
            router.setName(s.getName());
            router.setTitle(s.getTitle());
            menuRouters.add(router);
        });
        return RestResponse.success(menuRouters.stream().collect(Collectors.toMap(MenuRouters::getTitle, router -> router)));
    }


    @Override
    public List<Menu> findByRoles(Set<RoleSmallDto> roles) {
        List<Long> ids = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toList());
        return menuMapper.findMenusByRoleIds(ids);
    }


    private void getChildMenus(List<Menu> menuList, Set<Menu> menuSet) {
        for (Menu m : menuList) {
            menuSet.add(m);
            QueryWrapper<Menu> wrappers = new QueryWrapper<>();
            wrappers.eq("pid", m.getId());
            List<Menu> menus = menuMapper.selectList(wrappers);
            if (!ObjectUtils.isNull(menus) && menus.size() != 0) {
                getChildMenus(menus, menuSet);
            }
        }
    }


    private List<Menu> getMenus(Long pid) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq("pid", pid);
        return menuMapper.selectList(menuQueryWrapper);
    }


    private void updateSubCnt(Long menuId) {
        Long count = menuMapper.countByPid(menuId);
        menuMapper.updateSubCountById(menuId, count);
    }


    private List<String> getChildren(RouterDTO s, List<Menu> menus) {
        List<String> child = new ArrayList<>();
        menus.stream().filter(entity -> Objects.equals(entity.getPid(), s.getId())).peek(e -> child.add(e.getTitle())).collect(Collectors.toSet());
        return child;
    }


    private List<MenuDto> buildTree(List<Menu> menus) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        List<MenuDto> dtoS = convertor.convert(menus, MenuDto.class);
        for (MenuDto dto : dtoS) {
            if (null == dto.getPid()) {
                trees.add(dto);
            }
            for (MenuDto child : dtoS) {
                if (dto.getId().equals(child.getPid())) {
                    if (null == dto.getChildren()) {
                        dto.setChildren(new ArrayList<>());
                    }
                    dto.getChildren().add(child);
                    ids.add(child.getId());
                }
            }
        }
        //处理pid不存在的menus
        if (trees.size() == 0) {
            trees = dtoS.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }

        return trees;
    }
}
