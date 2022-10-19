package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.entity.Bot;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description bot相关的所有数据操作
 * @date 2022/10/18 21:18:18
 */
@Mapper
public interface BotMapper extends BaseMapper<Bot> {
}
