package com.kob.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kob.backend.entity.User;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.utils.EncDesUtil;
import com.kob.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description springSecurity的类
 * @date 2022/10/18 09:53:03
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    
    /**
     * @author fckey
     * @param username 用户名
     * @date 2022/10/18 9:54
     * @description 通过用户名，返回用户信息
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询数据
        User user  = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, EncDesUtil.enc(username)));
        if(null == user){
            throw new RuntimeException("用户不存在");
        }
        return new UserDetailsImpl(user);
    }
}
