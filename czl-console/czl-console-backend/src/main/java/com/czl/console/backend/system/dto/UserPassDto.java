package com.czl.console.backend.system.dto;

import lombok.Data;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/18
 * Description:
 */
@Data
public class UserPassDto {

    private String oldPass;

    private String newPass;
}
