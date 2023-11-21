package com.czl.console.backend.base.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.exception.CzlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    /**
     * 统一处理请求参数校验(实体对象传参)
     * @param e BindException
     * @return RestResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringPool.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return RestResponse.fail(message.toString());
    }


    @ExceptionHandler(value = CzlException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResponse handleCzlException(CzlException e) {
        log.error("Czl system error: {}", e.getMessage());
        return RestResponse.fail(e.getMessage());
    }


    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResponse handleRuntimeException(RuntimeException e) {
        log.error("runtime exception error: {}", e.getMessage());
        return RestResponse.fail(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResponse handleException(Exception e) {
        log.error("system internal error {}", e.getMessage());
        return RestResponse.fail(e.getMessage());
    }
}
