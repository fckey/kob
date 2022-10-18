package com.kob.backend.controller.user;

import com.kob.backend.entity.User;
import com.kob.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 有关于所有用户的操作
 * @date 2022/10/18 09:07:58
 */
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    /**
     * @author fckey
     * @params null
     * @date 2022/10/18 9:30
     * @description 查询所有的用户
     * @return java.util.List<com.kob.backend.entity.User>
     */
    @GetMapping("/user/all/")
    public List<User> getAll(){
        return userMapper.selectList(null);
    }
    
    /**
     * @author fckey
     * @param userId 用户的id
     * @date 2022/10/18 9:31
     * @description 通过用户的id查询用户
     * @return com.kob.backend.entity.User
     */
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId){
        return userMapper.selectById(userId);
    }
}
