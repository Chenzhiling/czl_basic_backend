package com.czl.console.backend.security.utils;

import cn.hutool.json.JSONObject;
import com.czl.console.backend.exception.CzlException;
import com.czl.console.backend.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/17
 * Description:
 */
@Slf4j
public class SecurityUtils {


    public static UserDetails getCurrentUserDetails() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }


    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CzlException("当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new CzlException("找不到当前登录的信息");
    }


    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUserDetails();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }
}
