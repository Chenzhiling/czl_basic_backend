package com.czl.console.backend.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.base.dozer.Convertor;
import com.czl.console.backend.security.utils.SecurityUtils;
import com.czl.console.backend.system.dao.RoleMapper;
import com.czl.console.backend.system.dao.UserMapper;
import com.czl.console.backend.system.dto.AddUserDto;
import com.czl.console.backend.system.dto.UpdateUserDto;
import com.czl.console.backend.system.dto.UserCriteria;
import com.czl.console.backend.system.dto.UserPassDto;
import com.czl.console.backend.system.entity.Role;
import com.czl.console.backend.system.entity.User;
import com.czl.console.backend.system.service.UserService;
import com.czl.console.backend.system.service.UsersRolesService;
import com.czl.console.backend.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: WANG QIUYE
 * Date: 2022/7/4
 * Description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User>
        implements UserService {

    @Value("${rsa.private_key}")
    private String privateKey;

    private final PasswordEncoder passwordEncoder;

    private final Convertor convertor;

    private final UsersRolesService usersRolesService;

    private final RoleMapper roleMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           Convertor convertor,
                           UsersRolesService usersRolesService, RoleMapper roleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.convertor = convertor;
        this.usersRolesService = usersRolesService;
        this.roleMapper = roleMapper;
    }

    @Override
    public Map<String, Object> addUserInfo(AddUserDto dto) {
        try {
            User user = convertor.convert(dto, User.class);
            user.setUserPassword(passwordEncoder.encode("123456"));
            user.setEnabled(dto.getEnabledFlag().equals("true"));
            this.save(user);
            usersRolesService.add(user.getId(),dto.getRoleIds());
            log.info("{} add user success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.info(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }



    }

    @Override
    public Map<String, Object> deleteUserInfo(Long id) {
        User user = this.getById(id);
        if (ObjectUtils.isEmpty(user)){
            return RestResponse.fail("user does not exist");
        }
        user.setIsDelete(true);
        try {
            this.updateById(user);
            usersRolesService.delete(id);
            log.info("{} delete user success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.info("delete user failed",e);
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> queryUser(UserCriteria criteria) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete",false);
        if (!ObjectUtils.isEmpty(criteria.getInput())) {
            wrapper.like("email",criteria.getInput()).or().like("user_name",criteria.getInput());
        }
        if (!ObjectUtils.isEmpty(criteria.getEnabled())) {
            wrapper.eq("enabled", criteria.getEnabled().equals("true"));
        }
        try {
            Page<User> page = new Page<>(criteria.getCurrent(), criteria.getSize());
            Page<User> userPage = this.page(page, wrapper);
            List<User> list = userPage.getRecords();
            for (User user : list) {
                Set<Role> roles = roleMapper.findByUserId(user.getId());
                user.setRoles(roles);
            }
            return RestResponse.success(list).put("total",userPage.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public User findUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete",false);
        wrapper.eq("user_name",name);
        User user = this.getOne(wrapper);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user;
    }

    @Override
    public Map<String, Object> updatePass(UserPassDto dto) {
        try {
            String oldPass = RsaUtils.decryptByPrivateKey(privateKey,dto.getOldPass());
            String newPass = RsaUtils.decryptByPrivateKey(privateKey,dto.getNewPass());
            String username = SecurityUtils.getCurrentUsername();
            User user = this.findUserByName(username);
            if (!passwordEncoder.matches(oldPass,user.getUserPassword())) {
                return RestResponse.fail("update password failed, ole password is not correct");
            }
            if (passwordEncoder.matches(newPass, user.getUserPassword())){
                return RestResponse.fail("The new password cannot be the same as the old password");
            }
            user.setUserPassword(passwordEncoder.encode(newPass));
            try {
                this.updateById(user);
                log.info("{} update password success",SecurityUtils.getCurrentUsername());
                return RestResponse.success();
            } catch (Exception e) {
                log.error("update password failed",e);
                return RestResponse.fail(e.getMessage());
            }
        } catch (Exception e) {
            log.error("update password failed",e);
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> setEnable(Long id) {
        User user = this.getById(id);
        user.setEnabled(!user.getEnabled());
        try {
            this.updateById(user);
            log.info("{} set user enable success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> updateUser(UpdateUserDto dto) {
        User user = convertor.convert(dto,User.class);
        user.setEnabled(dto.getEnabledFlag().equals("true"));
        try {
            this.updateById(user);
            usersRolesService.delete(user.getId());
            usersRolesService.add(user.getId(),dto.getRoleIds());
            log.info("{} update user success",SecurityUtils.getCurrentUsername());
            return RestResponse.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestResponse.fail(e.getMessage());
        }
    }
}

