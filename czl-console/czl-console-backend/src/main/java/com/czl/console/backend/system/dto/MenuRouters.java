package com.czl.console.backend.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author CHEN ZHI LING
 * @date 2022/8/23
 * @desc:
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuRouters {
    private String name;
    private String icon;
    private String path;
    private String component;
    @JsonIgnore
    private String title;
}
