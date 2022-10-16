package com.kob.backend.controller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tourist
 * @version 1.0
 * @project backend
 * @description 返回的是index的所有页面等重要的索引信息
 * @date 2022/10/17 07:15:21
 */
@Controller
@RequestMapping("/pk/")
public class IndexController {
    @RequestMapping("index")
    public String index(){
        return "pk/index.html";
    }
}
