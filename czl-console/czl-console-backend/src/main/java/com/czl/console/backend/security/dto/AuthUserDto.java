package com.czl.console.backend.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/07/28
 * Description: 用户登录参数
 */
@Data
public class AuthUserDto {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为为空")
    private String password;

    private String code;

    private String uuid = "";
}
