package com.kob.backend.service.impl.user.account;

import com.kob.backend.entity.User;
import com.kob.backend.service.user.account.LoginService;
import com.kob.backend.utils.JwtUtil;
import com.kob.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 用户登录
 * @date 2022/10/18 11:29:15
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager  authenticationManager;

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
}
