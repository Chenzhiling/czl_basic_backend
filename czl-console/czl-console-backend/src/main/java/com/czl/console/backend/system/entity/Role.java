package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import lombok.Data;

import java.util.Set;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/5
 * Description:
 */
@Data
@TableName(value = "sys_role",autoResultMap = true)
public class Role extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "description")
    private String description;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "data_scope")
    private String dataScope;

    @TableField(exist = false)
    private Set<Menu> menus;
}
