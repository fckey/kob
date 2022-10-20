package com.kob.backend.utils;

import io.jsonwebtoken.Claims;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description jwt的工具类
 * @date 2022/10/19 20:24:17
 */
public class JwtAuthenticationUtil {
    
    /**
     * @author fckey
     * @param token jwt验证的token
     * @date 2022/10/19 20:25
     * @description 通过token解析出user的id
     * @return java.lang.Integer
     */
    public static Integer getUserId(String token){
        Integer userId = -1;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}
