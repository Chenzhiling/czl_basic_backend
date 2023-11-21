package com.czl.console.backend.system.dto;

import lombok.Data;

import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/16
 * Description:
 */
@Data
public class RoleMenusDto {

    private Long id;

    private Set<Long> menus;
}
