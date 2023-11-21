package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/5
 * Description:
 */
@Data
@TableName("sys_users_roles")
public class UsersRoles {

    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id", required = true)
    private Long userID;

    @TableField(value = "role_id")
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;
}
