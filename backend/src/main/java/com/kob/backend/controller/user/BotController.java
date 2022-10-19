package com.kob.backend.controller.user;

import com.kob.backend.entity.Bot;
import com.kob.backend.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description bot的控制器
 * @date 2022/10/18 22:05:55
 */
@RestController
public class BotController {

    @Autowired
    private BotService botService;
    
    /**
     * @author fckey
     * @param data
     * @date 2022/10/18 22:07
     * @description 添加操作
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/bot/add")
    public Map<String, String> add(@RequestParam Map<String, String> data){
        return botService.add(data);
    }
    
    /**
     * @author fckey
     * @param data
     * @date 2022/10/19 9:21
     * @description 删除指定的bot
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/bot/remove")
    public Map<String, String> remove(@RequestParam Map<String, String> data){
        return  botService.remove(data);
    }

    /**
     * @author fckey
     * @params
     * @param data
     * @date 2022/10/19 9:54
     * @description 更新指定的bot
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    @PostMapping("/user/bot/update")
    public Map<String, String> update(@RequestParam Map<String, String> data){
        return botService.update(data);
    }
    
    /**
     * @author fckey
     * @date 2022/10/19 10:19
     * @description 查询出当前用户所有的bot
     * @return java.util.List<com.kob.backend.entity.Bot>
     */
    @GetMapping("/user/bot/getlist")
    public List<Bot> getList(){
        return botService.getList();
    }

}
