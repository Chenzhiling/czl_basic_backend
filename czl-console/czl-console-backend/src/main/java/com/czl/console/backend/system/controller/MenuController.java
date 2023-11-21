package com.czl.console.backend.system.controller;

import com.czl.console.backend.system.entity.Menu;
import com.czl.console.backend.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "菜单信息管理")
@RestController
@RequestMapping("menu/")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation("获取所有菜单信息(不递归)")
    @GetMapping("listAll")
    public ResponseEntity<Object> listAll() {
        return new ResponseEntity<>(menuService.listALl(), HttpStatus.OK);
    }

    @ApiOperation("展示所有路由信息")
    @GetMapping("listMenus")
    public ResponseEntity<Object> listMenus() {
        return new ResponseEntity<>(menuService.listMenus(), HttpStatus.OK);
    }

    @ApiOperation("新增菜单信息")
    @PostMapping(value = "addMenu")
    public ResponseEntity<Object> addMenu(@Valid @RequestBody Menu menu) {
        return new ResponseEntity<>(menuService.addMenu(menu), HttpStatus.OK);
    }


    @ApiOperation("删除菜单信息")
    @PostMapping(value = "deleteMenu")
    public ResponseEntity<Object> deleteMenu(@Valid @RequestParam Long id) {
        return new ResponseEntity<>(menuService.deleteMenu(id), HttpStatus.OK);
    }


    @ApiOperation("根据pid查找所有菜单信息")
    @PostMapping(value = "findMenusByPid")
    public ResponseEntity<Object> findMenusByPid(@Valid @RequestParam Long pid) {
        return new ResponseEntity<>(menuService.findMenusByPid(pid), HttpStatus.OK);
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @PostMapping(value = "buildTree")
    public ResponseEntity<Object> buildTree() {
        return new ResponseEntity<>(menuService.buildTree(), HttpStatus.OK);
    }
}
