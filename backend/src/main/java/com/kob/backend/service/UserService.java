package com.kob.backend.service;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 对user有关的操作
 * @date 2022/10/18 21:22:11
 */
public interface UserService {
    /**
     * @author fckey
     * @date 2022/10/18 11:28
     * @description 获取用户的信息
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> getInfo();
    /**
     * @author fckey
     * @param username 用户名
     * @param password 密码
     * @date 2022/10/18 11:28
     * @description 登录的接口
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> getToken(String username, String password);

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
