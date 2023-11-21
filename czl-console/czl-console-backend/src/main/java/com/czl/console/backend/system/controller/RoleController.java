package com.czl.console.backend.system.controller;

import com.czl.console.backend.aop.log.Log;
import com.czl.console.backend.system.dto.AddRoleDto;
import com.czl.console.backend.system.dto.RoleMenusDto;
import com.czl.console.backend.system.dto.RoleQueryCriteria;
import com.czl.console.backend.system.dto.UpdateRoleDto;
import com.czl.console.backend.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "角色信息管理")
@RestController
@RequestMapping("role/")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @Log("新增角色")
    @ApiOperation("新增角色信息")
    @PreAuthorize("@czl.check('role:add')")
    @PostMapping(value = "addRole")
    public ResponseEntity<Object> addRole(@Valid @RequestBody AddRoleDto addRoleDto) {
        return new ResponseEntity<>(roleService.addRole(addRoleDto), HttpStatus.OK);
    }


    @Log("删除角色")
    @ApiOperation("删除角色信息")
    @PreAuthorize("@czl.check('role:delete')")
    @PostMapping(value = "deleteRole")
    public ResponseEntity<Object> deleteRole(@Valid @RequestParam Long id) {
        return new ResponseEntity<>(roleService.deleteRole(id), HttpStatus.OK);
    }


    @ApiOperation("查询角色")
    @PostMapping(value = "queryRoles")
    public ResponseEntity<Object> queryRoles(@Valid @RequestBody RoleQueryCriteria criteria) {
        return new ResponseEntity<>(roleService.queryRoles(criteria), HttpStatus.OK);
    }


    @ApiOperation("按需返回角色所对应的菜单信息")
    @GetMapping(value = "queryRolesBy/{id}")
    public ResponseEntity<Object> queryRolesBy(@PathVariable("id") long id) {
        return new ResponseEntity<>(roleService.queryRolesBy(id), HttpStatus.OK);
    }


    @Log("修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PreAuthorize("@czl.check('roleMenu:update')")
    @PostMapping(value = "/changeRoleMenu")
    public ResponseEntity<Object> changeRoleMenu(@RequestBody RoleMenusDto dto) {
        return new ResponseEntity<>(roleService.changeRoleMenu(dto), HttpStatus.OK);
    }


    @Log("更新角色")
    @ApiOperation("更新角色")
    @PreAuthorize("@czl.check('role:update')")
    @PostMapping(value = "/updateRole")
    public ResponseEntity<Object> updateRole(@RequestBody UpdateRoleDto dto) {
        return new ResponseEntity<>(roleService.updateRole(dto), HttpStatus.OK);
    }


    @ApiOperation("获得所有角色信息")
    @GetMapping(value = "/all")
    public ResponseEntity<Object> queryAllRole() {
        return new ResponseEntity<>(roleService.queryAllRole(), HttpStatus.OK);
    }
}
