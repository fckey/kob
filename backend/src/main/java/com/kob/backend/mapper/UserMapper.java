package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 有关于用户的所有数据库操作
 * @date 2022/10/18 09:20:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
