package com.kob.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kob.backend.entity.User;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.service.UserService;
import com.kob.backend.utils.JwtUtil;
import com.kob.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @description userservice有关实现类
 * @date 2022/10/18 21:23:07
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getInfo() {
        // 从上下文中提取到已经登录的数据
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl)token.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getDecUserName());
        map.put("photo", user.getPhoto());
        return map;
    }

    @Override
    public Map<String, String> getToken(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // 验证是否可以正常登录， 如果没有正常登录，就会抛出异常
        Authentication authenticate = authenticationManager.authenticate(token);
        // 取出用户名
        UserDetailsImpl loginUser = (UserDetailsImpl)authenticate.getPrincipal();
        User user = loginUser.getUser();
        // 将userid进行加密处理
        String jwt = JwtUtil.createJWT(user.getId().toString());

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("token", jwt);

        return map;
    }

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
        User user = new User(null, null, encodedPassword, photo);
        // 设置加密密码
        user.setEncUserName(username);
        user.insert();
        map.put("error_message", "success");
        return map;
    }
}
