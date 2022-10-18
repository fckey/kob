package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 获取用户信息控制
 * @date 2022/10/18 14:26:20
 */
@RestController
public class InfoServiceController {

    @Autowired
    private InfoService infoService;
    
    /**
     * @author fckey
     * @date 2022/10/18 14:27
     * @description 获取已经登录用户的信息
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @GetMapping("/user/account/info")
    public Map<String, String> getInfo(){
        return infoService.getInfo();
    }
}
