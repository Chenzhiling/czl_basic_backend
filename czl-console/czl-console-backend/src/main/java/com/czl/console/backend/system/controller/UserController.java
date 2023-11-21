package com.czl.console.backend.system.controller;

import com.czl.console.backend.aop.log.Log;
import com.czl.console.backend.system.dto.AddUserDto;
import com.czl.console.backend.system.dto.UpdateUserDto;
import com.czl.console.backend.system.dto.UserCriteria;
import com.czl.console.backend.system.dto.UserPassDto;
import com.czl.console.backend.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/4
 * Description: 用户信息
 */

@Api(tags = "用户信息管理")
@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userinfoService;

    public UserController(UserService userinfoService) {

        this.userinfoService = userinfoService;
    }

    @Log("新增用户")
    @PreAuthorize("@czl.check('user:add')")
    @ApiOperation("新增用户信息")
    @PostMapping(value = "add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody AddUserDto dto){
        return new ResponseEntity<>(userinfoService.addUserInfo(dto), HttpStatus.OK);
    }


    @Log("删除用户")
    @PreAuthorize("@czl.check('user:delete')")
    @ApiOperation("删除用户信息")
    @PostMapping(value = "delete")
    public ResponseEntity<Object> deleteUser(@RequestParam Long id){
        return new ResponseEntity<>(userinfoService.deleteUserInfo(id), HttpStatus.OK);
    }


    @ApiOperation("高级筛选用户信息")
    @PostMapping(value = "query")
    public ResponseEntity<Object> queryUser(@RequestBody UserCriteria criteria){
        return new ResponseEntity<>(userinfoService.queryUser(criteria), HttpStatus.OK);
    }


    @Log("修改密码")
    @ApiOperation("修改密码")
    @PostMapping(value = "updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassDto dto){
        return new ResponseEntity<>(userinfoService.updatePass(dto), HttpStatus.OK);
    }


    @ApiOperation("改变用户账号状态")
    @PostMapping(value = "setEnable")
    public ResponseEntity<Object> setEnable(@RequestParam Long id){
        return new ResponseEntity<>(userinfoService.setEnable(id), HttpStatus.OK);
    }


    @Log("更新用户")
    @PreAuthorize("@czl.check('user:update')")
    @ApiOperation("更新用户")
    @PostMapping(value = "update")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserDto dto){
        return new ResponseEntity<>(userinfoService.updateUser(dto), HttpStatus.OK);
    }
}
