package com.czl.console.backend.system.dto;

import lombok.Data;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/9/9
 * Description:
 */
@Data
public class UpdateRoleDto {

    private Long id;

    private String name;

    private String description;

    private Integer level;

    private String dataScope;
}
