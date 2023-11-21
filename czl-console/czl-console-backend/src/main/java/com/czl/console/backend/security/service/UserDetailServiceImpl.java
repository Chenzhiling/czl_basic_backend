package com.czl.console.backend.security.service;

import com.czl.console.backend.exception.CzlException;
import com.czl.console.backend.security.dto.JwtUserDto;
import com.czl.console.backend.system.entity.User;
import com.czl.console.backend.system.service.RoleService;
import com.czl.console.backend.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/28
 * Description:
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    public UserDetailServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!user.getEnabled()) {
            throw new CzlException("账号未激活");
        }
        return new JwtUserDto(user, roleService.mapToGrantedAuthorities(user));
    }
}
