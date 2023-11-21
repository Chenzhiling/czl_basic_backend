package com.czl.console.backend.security.config;

import com.czl.console.backend.security.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/17
 * Description:
 */
@Service(value = "czl")
public class PermissionConfig {

    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> nhPermissions = SecurityUtils.getCurrentUserDetails().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return nhPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(nhPermissions::contains);
    }
}
