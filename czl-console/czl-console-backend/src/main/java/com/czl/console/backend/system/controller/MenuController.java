package com.czl.console.backend.system.controller;

import com.czl.console.backend.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping(value = "buildTree")
    public ResponseEntity<Object> buildTree() {
        return new ResponseEntity<>(menuService.buildTree(), HttpStatus.OK);
    }
}
