package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Author: WANG QIUYE
 * Date: 2022/7/4
 * Description: 用户信息录入
 */
@Data
@TableName(value = "sys_user",autoResultMap = true)
public class User extends BaseEntity {

    @TableField(value = "user_name")
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @TableField(value = "password")
    @ApiModelProperty(value = "密码",required = true)
    private String userPassword;

    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @TableField(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    @NotNull
    @TableField(value = "enabled")
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @TableField(value = "is_admin")
    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin = false;

    /** 用户角色 */
    @TableField(exist = false)
    private Set<Role> roles;
}
