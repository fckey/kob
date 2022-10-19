package com.kob.backend.utils;

import com.kob.backend.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 公共的工具类
 * @date 2022/10/19 09:37:53
 */
public class CommonUtils {
    
    /**
     * @author fckey
     * @date 2022/10/19 9:40
     * @description 获取当前登录用户
     * @return com.kob.backend.entity.User
     */
    public static User getLoginUser(){
        UsernamePasswordAuthenticationToken token  = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) token.getPrincipal();
        return loginUser.getUser();
    }
}
