package com.kob.backend.service.user.account;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 登录相关的借口
 * @date 2022/10/18 11:21:17
 */
public interface LoginService {

    /**
     * @author fckey
     * @param username 用户名
     * @param password 密码
     * @date 2022/10/18 11:28
     * @description 登录的接口
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> getToken(String username, String password);

}
