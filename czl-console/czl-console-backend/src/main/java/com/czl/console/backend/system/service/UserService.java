package com.czl.console.backend.system.service;

import com.czl.console.backend.system.dto.AddUserDto;
import com.czl.console.backend.system.dto.UpdateUserDto;
import com.czl.console.backend.system.dto.UserCriteria;
import com.czl.console.backend.system.dto.UserPassDto;
import com.czl.console.backend.system.entity.User;
import java.util.Map;


/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/4
 * Description:
 */
public interface UserService extends BaseService<User> {
    /**
     * 新增用户信息
     */
    Map<String,Object> addUserInfo(AddUserDto dto);

    /**
     * 删除用户信息
     */
    Map<String,Object> deleteUserInfo(Long id);


    /**
     * 高级筛选所有用户信息
     */
    Map<String,Object> queryUser(UserCriteria criteria);


    /**
     * 根据用户名查找用户
     */
    User findUserByName(String name);


    /**
     * 修改密码
     */
    Map<String,Object> updatePass(UserPassDto dto);


    /**
     * 设置用户状态
     */
    Map<String,Object> setEnable(Long id);

    /**
     * 更新用户
     */
    Map<String,Object> updateUser(UpdateUserDto dto);
}

