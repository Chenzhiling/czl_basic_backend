package com.czl.console.backend.system.dto;

import com.czl.console.backend.base.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class MenuDto extends BaseEntity {

    private Long parentId;

    private String name;

    private String icon;

    private int hidden;

    private int level;

    private int sort;

    private String title;

    private String permission;

    private String type;

    private List<MenuDto> children;

}
