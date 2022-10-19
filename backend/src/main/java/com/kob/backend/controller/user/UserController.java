package com.kob.backend.controller.user;

import com.kob.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 对user有关的controller
 * @date 2022/10/18 21:24:53
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @author fckey
     * @date 2022/10/18 14:27
     * @description 获取已经登录用户的信息
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @GetMapping("/user/account/info")
    public Map<String, String> getInfo(){
        return userService.getInfo();
    }

    /**
     * @author fckey
     * @param map
     * @date 2022/10/18 21:28
     * @description 通过用户名密码获取token
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/account/token")
    public Map<String, String> getToken(@RequestParam Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        return userService.getToken(username, password);
    }
    
    /**
     * @author fckey
     * @param map
     * @date 2022/10/18 21:28
     * @description 注册用户信息
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/account/register")
    public Map<String, String> register(@RequestParam Map<String, String > map){
        String username = map.get("username");
        String password = map.get("password");
        String confirmedPassword = map.get("confirmedPassword");
        return userService.register(username, password, confirmedPassword);
    }
}
