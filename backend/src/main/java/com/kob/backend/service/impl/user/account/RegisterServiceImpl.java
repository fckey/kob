package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kob.backend.entity.User;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 注册相关功能的业务书写
 * @date 2022/10/18 14:33:58
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String,  String> map = new HashMap<>();
        if(username == null){
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if(password == null || confirmedPassword == null){
            map.put("error_message", "密码不能为空");
            return map;
        }
        username = username.trim();
        if(username.length() == 0){
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if(password.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message", "密码长度不能为空");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message", "用户名长度不能大于100");
            return map;
        }
        if(password.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message", "用户的密码不能大于100");
            return map;
        }
        if(!password.equals(confirmedPassword)){
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }

        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if(!CollectionUtils.isEmpty(users)){
            map.put("error_message", "用户名已存在");
            return map;
        }
        // 加密密码
        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/1_sm_844c66b332.jpg";
        User user = new User(null, username, encodedPassword, photo);
        user.insert();
        map.put("error_message", "success");
        return map;
    }
}
