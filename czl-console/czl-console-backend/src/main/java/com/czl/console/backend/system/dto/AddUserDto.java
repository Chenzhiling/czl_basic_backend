package com.czl.console.backend.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/10
 * Description:
 */
@Data
public class AddUserDto {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    private String email;

    private String enabledFlag;

    private List<Long> roleIds;

}
