package com.kob.backend.service;

import com.kob.backend.entity.Bot;

import java.util.List;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description bot有关的service
 * @date 2022/10/18 21:30:08
 */
public interface BotService {
    
    /**
     * @author fckey
     * @param data
     * @date 2022/10/18 21:31
     * @description 添加数据
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> add(Map<String, String> data);

    Map<String, String> remove(Map<String, String> data);

    Map<String, String> update(Map<String, String> data);

    List<Bot> getList();

}
