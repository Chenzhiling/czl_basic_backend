package com.czl.console.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/10
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDto {

    private String userName;

    /**
     * IP
     */
    private String ip;

    /**
     * token
     */
    private String key;
    /**
     * 登录时间
     */
    private Date loginTime;
}
