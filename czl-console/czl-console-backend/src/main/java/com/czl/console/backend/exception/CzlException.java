package com.czl.console.backend.exception;

import lombok.Getter;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/5/7 15:04
 * Description: 系统内部异常
 */
@Getter
public class CzlException extends RuntimeException {

    private static final long serialVersionUID = -994962710559017255L;

    public CzlException(String message) {
        super(message);
    }

    public CzlException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }
}
