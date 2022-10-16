package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tourist
 * @version 1.0
 * @project backend
 * @description bot的有关信息类
 * @date 2022/10/17 07:17:15
 */
@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getBotInfo")
    public List<String> getBotInfo(){
        List<String> list = new ArrayList<>();
        list.add("tset");
        list.add("b");
        return list;
    }
}
