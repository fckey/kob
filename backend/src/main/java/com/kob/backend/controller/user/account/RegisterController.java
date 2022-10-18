package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 注册的控制器
 * @date 2022/10/18 14:49:55
 */
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    
    /**
     * @author fckey
     * @param map
     * @date 2022/10/18 14:53
     * @description 注册
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/account/register")
    public Map<String, String> register(@RequestParam Map<String, String > map){
        String username = map.get("username");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmPassword");
        return registerService.register(username, password, confirmedPassword);
    }
}
