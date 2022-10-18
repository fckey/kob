package com.kob.backend.service.user.account;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 获取信息相关的接口
 * @date 2022/10/18 11:21:39
 */
public interface InfoService {
    
    /**
     * @author fckey
     * @date 2022/10/18 11:28
     * @description 获取用户的信息
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> getInfo();
}
