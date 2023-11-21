package com.czl.console.backend.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/11/10
 * Description:
 */
@Data
public class AddRoleDto {


    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "角色级别")
    private Integer level;

    @ApiModelProperty(value = "数据权限")
    private String dataScope;

    private List<Long> menusId;
}
