package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description bot的有关信息类
 * @date 2022/10/17 07:17:15
 */
@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getBotInfo")
    public Map<String, String> getBotInfo(){
        Map<String, String> map = new HashMap<>();
        map.put("bot_name", "fckey");
        map.put("bot_rating", "1");
        return map;
    }
}
