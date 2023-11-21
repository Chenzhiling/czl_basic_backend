package com.czl.console.backend.security.utils;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/29
 * Description:
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException exception) throws IOException {
        //当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                exception == null ? "Unauthorized": exception.getMessage());
    }
}
