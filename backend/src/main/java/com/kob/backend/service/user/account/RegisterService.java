package com.kob.backend.service.user.account;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 注册的接口
 * @date 2022/10/18 11:22:30
 */
public interface RegisterService {

    /**
     * @author fckey
     * @param username 用户名
     * @param password 密码
     * @param confirmedPassword 确认的密码
     * @date 2022/10/18 11:26
     * @description 用户注册
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> register(String username, String password, String confirmedPassword);
}
