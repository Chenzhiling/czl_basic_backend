package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName(value = "sys_menu", autoResultMap = true)
public class Menu extends BaseEntity {

    @TableField(value = "parentId")
    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;

    @TableField(value = "name")
    @ApiModelProperty(value = "组件名称")
    private String name;

    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    @TableField(value = "hidden")
    @ApiModelProperty(value = "隐藏")
    private int hidden;

    @TableField(value = "type")
    @ApiModelProperty(value = "菜单类型")
    private int level;

    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private int sort;

    @TableField(value = "title")
    @ApiModelProperty(value = "菜单标题")
    @NotBlank(message = "菜单标题不能为空")
    private String title;


    @TableField(value = "permission")
    @ApiModelProperty(value = "权限")
    private String permission;

    @TableField(value = "type")
    @ApiModelProperty(value = "类型")
    private String type;

}
