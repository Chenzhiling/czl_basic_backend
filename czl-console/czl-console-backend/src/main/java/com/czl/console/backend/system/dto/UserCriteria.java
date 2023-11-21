package com.czl.console.backend.system.dto;

import com.czl.console.backend.base.domain.BaseCriteria;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/9/14
 * Description:
 */

public class UserCriteria extends BaseCriteria {

    private String input;

    private String enabled;

    public String getInput() {
        return input;
    }

    public String getEnabled() {
        return enabled;
    }

}
