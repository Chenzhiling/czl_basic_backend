package com.czl.console.backend.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author CHEN ZHI LING
 * @date 2022/8/17
 * @desc:
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterDTO {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long pid;
    private String router;
    private String name;
    private String icon;
    private String path;
    @JsonIgnore
    private Integer menuSort;
    private List<String> children;
}
