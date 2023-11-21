package com.czl.console.backend.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/16
 * Description:
 */
@Data
public class RoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
