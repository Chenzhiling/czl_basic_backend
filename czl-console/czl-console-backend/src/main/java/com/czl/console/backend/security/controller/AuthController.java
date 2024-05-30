package com.czl.console.backend.security.controller;

import com.czl.console.backend.aop.AnonymousAccess;
import com.czl.console.backend.base.domain.RestResponse;
import com.czl.console.backend.security.config.SecurityProperties;
import com.czl.console.backend.security.dto.AuthUserDto;
import com.czl.console.backend.security.dto.JwtUserDto;
import com.czl.console.backend.security.service.OnlineUserService;
import com.czl.console.backend.security.utils.TokenProvider;
import com.czl.console.backend.utils.RsaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/28
 * Description:
 */
@Api(tags = "系统授权接口")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Value("${rsa.private_key}")
    private String privateKey;

    @Value("${single.login}")
    private Boolean singleLogin;

    private final SecurityProperties properties;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(SecurityProperties properties,
                          TokenProvider tokenProvider,
                          OnlineUserService onlineUserService,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.tokenProvider = tokenProvider;
        this.onlineUserService = onlineUserService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthUserDto dto, HttpServletRequest request) throws Exception {

        String password = RsaUtils.decryptByPrivateKey(privateKey, dto.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if(singleLogin){
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(jwtUserDto.getUsername(), token);
        }
        return new ResponseEntity<>(RestResponse.success(authInfo), HttpStatus.OK);
    }


    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("查询在线用户")
    @AnonymousAccess
    @PostMapping(value = "getAll")
    public ResponseEntity<Object> getAll(@Valid @RequestParam String filter) {
        return new ResponseEntity<>(onlineUserService.getAll(filter), HttpStatus.OK);
    }
}
