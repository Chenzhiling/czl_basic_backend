package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "sys_roles_menus",autoResultMap = true)
public class RolesMenus {


    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;

    @TableField(value = "menu_id")
    @ApiModelProperty(value = "菜单id", required = true)
    private Long menuId;
}
