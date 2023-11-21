package com.czl.console.backend.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.czl.console.backend.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName(value = "sys_menu",autoResultMap = true)
public class Menu extends BaseEntity {

    @TableField(value = "pid")
    @ApiModelProperty(value = "上级菜单id")
    private Long pid;

    @TableField(value = "sub_count")
    @ApiModelProperty(value = "子菜单数目",hidden = true)
    private Integer subCount = 0;

    @TableField(value = "type")
    @ApiModelProperty(value = "菜单类型")
    private int type;

    @TableField(value = "title")
    @ApiModelProperty(value = "菜单标题")
    @NotBlank(message = "菜单标题不能为空")
    private String title;

    @TableField(value = "name")
    @ApiModelProperty(value = "组件名称")
    private String name;

    @TableField(value = "component")
    @ApiModelProperty(value = "组件")
    private String component;

    @TableField(value = "menu_sort")
    @ApiModelProperty(value = "排序")
    private int menuSort;

    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    @TableField(value = "path")
    @ApiModelProperty(value = "链接地址")
    private String path;

    @TableField(value = "i_frame")
    @ApiModelProperty(value = "是否外链")
    private Boolean iFrame;

    @TableField(value = "cache")
    @ApiModelProperty(value = "缓存")
    private Boolean cache;

    @TableField(value = "hidden")
    @ApiModelProperty(value = "隐藏")
    private Boolean hidden;

    @TableField(value = "permission")
    @ApiModelProperty(value = "权限")
    private String permission;
}
