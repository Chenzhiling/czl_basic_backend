package com.czl.console.backend.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private Long id;

    private Long pid;

    private List<MenuDto> children;

    private int type;

    private String title;

    private String name;

    private String component;

    private int menuSort;

    private String icon;

    private String path;

    private Boolean iFrame;

    private Boolean hidden;

    private String permission;
}
