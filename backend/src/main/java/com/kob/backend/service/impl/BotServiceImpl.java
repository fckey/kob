package com.kob.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.entity.Bot;
import com.kob.backend.entity.User;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.service.BotService;
import com.kob.backend.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description Bot有关的实现
 * @date 2022/10/18 21:34:03
 */
@Service
public class BotServiceImpl implements BotService {
    @Autowired
    private BotMapper botMapper;
    
    /**
     * @author fckey
     * @param data
     * @date 2022/10/18 22:05
     * @description 用户的添加
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public Map<String, String> add(Map<String, String> data) {
        // 获取当前用户
        User user = CommonUtils.getLoginUser();
        String title  = data.get("title");
        String remark = data.get("remark");
        String content = data.get("content");
        // 校验
        Map<String, String> map = new HashMap<>();
        if(title == null || title.length() == 0){
            map.put("error_message", "标题不能为空");
            return map;
        }
        if(title.length() > 1000){
            map.put("error_message", "标题长度不能大于100");
            return map;
        }
        if(remark == null || remark.length() == 0){
            remark = "这个用户很懒，什么也没有留下~~~";
        }
        if(remark != null && remark.length() > 300){
            map.put("error_message", "标题的长度不能大于300");
            return map;
        }
        if(content == null || content.length() == 0){
            map.put("error_message", "代码不能为空");
            return map;
        }
        if(content.length() > 10000){
            map.put("error_message", "代码长度不能超多10000");
            return map;
        }
        // 逻辑书写
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, remark, "", 1500, now, now, "", "");
        bot.setEncContent(content);
        bot.setEncVerify(bot);
        bot.insert();
        map.put("error_message", "success");
        return map;
    }

    /**
     * @author fckey
     * @param data
     * @date 2022/10/19 9:22
     * @description 删除指定的bot
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public Map<String, String> remove(Map<String, String> data) {
        User user = CommonUtils.getLoginUser();
        int botId = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(botId);

        Map<String, String> map = new HashMap<>();
        if(bot == null ){
            map.put("error_message", "bot不存在或者是已经删除");
            return map;
        }
        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message", "没有权限删除bot功能");
            return map;
        }
        // 删除指定数据
        botMapper.deleteById(botId);
        map.put("error_message", "success");
        return map;
    }

    /**
     * @author fckey
     * @param data
     * @date 2022/10/19 9:53
     * @description 更新bot
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @Override
    public Map<String, String> update(Map<String, String> data) {
        User user = CommonUtils.getLoginUser();


        Map<String, String> map = new HashMap<>();

        String title  = data.get("title");
        String remark = data.get("remark");
        String content = data.get("content");
        if(title == null || title.length() == 0){
            map.put("error_message", "标题不能为空");
            return map;
        }
        if(title.length() > 1000){
            map.put("error_message", "标题长度不能大于100");
            return map;
        }
        if(remark == null || remark.length() == 0){
            remark = "这个用户很懒，什么也没有留下~~~";
        }
        if(remark != null && remark.length() > 300){
            map.put("error_message", "标题的长度不能大于300");
            return map;
        }
        if(content == null || content.length() == 0){
            map.put("error_message", "代码不能为空");
            return map;
        }
        if(content.length() > 10000){
            map.put("error_message", "代码长度不能超多10000");
            return map;
        }
        int botId = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(botId);
        if(bot == null){
            map.put("error_message", "bot不存在或已被删除");
            return map;
        }

        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message", "没有权限修改当前bot");
            return map;
        }
        // 更新
        bot.setEncContent(content);
        bot.setTitle(title);
        bot.setRemark(remark);
        bot.setEncVerify(bot);
        botMapper.updateById(bot);

        map.put("error_message", "success");
        return map;
    }
    
    /**
     * @author fckey
     * @date 2022/10/19 10:14
     * @description 获得当前用户所有的bot
     * @return java.util.List<com.kob.backend.entity.Bot>
     */
    @Override
    public List<Bot> getList() {
        User user = CommonUtils.getLoginUser();
        QueryWrapper<Bot> queryWrapper = new QueryWrapper<Bot>();
        queryWrapper.eq("user_id", user.getId());
        return botMapper.selectList(queryWrapper);
    }
}
