package com.czl.console.backend.security.service;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.czl.console.backend.security.config.SecurityProperties;
import com.czl.console.backend.security.dto.JwtUserDto;
import com.czl.console.backend.security.dto.OnlineUserDto;
import com.czl.console.backend.utils.EncryptUtils;
import com.czl.console.backend.utils.RedisUtils;
import com.czl.console.backend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/8/10
 * Description:
 */
@Slf4j
@Service
public class OnlineUserService {

    private final RedisUtils redisUtils;
    private final SecurityProperties properties;

    public OnlineUserService(RedisUtils redisUtils, SecurityProperties properties) {
        this.redisUtils = redisUtils;
        this.properties = properties;
    }

    public void save(JwtUserDto dto, String token, HttpServletRequest request) {
        String ip = StringUtils.getIp(request);
        OnlineUserDto onlineUser = null;
        try {
            onlineUser = new OnlineUserDto(dto.getUsername(), ip, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将token存入redis
        redisUtils.set(properties.getOnlineKey() + token,
                onlineUser, properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 查询某个用户
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisUtils.get(key);
    }



    public List<OnlineUserDto> getAll(String filter) {
        //获得所有的key
        List<String> keys = redisUtils.scan(properties.getOnlineKey() + "*");
        Collections.reverse(keys);
        List<OnlineUserDto> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDto onlineUser = (OnlineUserDto) redisUtils.get(key);
            if (StringUtils.isNotBlank(filter)) {
                if(onlineUser.toString().contains(filter)){
                    onlineUsers.add(onlineUser);
                }
            } else {
                onlineUsers.add(onlineUser);
            }
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }

    /**
     * 检查在线用户
     */
    public void checkLoginOnUser(String username, String newToken) {
        List<OnlineUserDto> allOnlineUsers = getAll(username);
        if (ObjectUtils.isEmpty(allOnlineUsers)) {
            return;
        }
        for (OnlineUserDto onlineUser : allOnlineUsers) {
            try {
                String oldToken = EncryptUtils.desDecrypt(onlineUser.getKey());
                if (StringUtils.isNotBlank(newToken) && !newToken.equals(oldToken)) {
                    this.kickOut(oldToken);
                } else if (StringUtils.isBlank(newToken)) {
                    this.kickOut(oldToken);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 删除token
     */
    public void kickOut(String key){
        key = properties.getOnlineKey() + key;
        redisUtils.del(key);
    }

    /**
     * 退出登录
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisUtils.del(key);
    }
}
