package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.constant.CommonConstant;
import com.kob.backend.entity.User;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.utils.JwtAuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author fckey
 * @version 1.0
 * @project backend
 * @description 和前段进行连接的类
 * @date 2022/10/19 15:58:06
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    private final static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    /**
     * 匹配池
     */
    private final static CopyOnWriteArraySet<User> matchPool = new CopyOnWriteArraySet<>();
    private User user;
    private Session session = null;
    /**
     * 静态变量的注入方式
     */
    private static UserMapper userMapper;
    @Autowired
    private void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        log.info("connected!");
        // 从token解析出id
        Integer userId = JwtAuthenticationUtil.getUserId(token);
        // 按照id取得用户
        this.user = userMapper.selectById(userId);
        if(this.user != null){
            // 将当前用户放到map中
            users.put(userId, this);
        }else{
            this.session.close();
        }
        log.info(user.toString());
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        log.info("close");
        if(this.user != null){
            users.remove(this.user.getId());
            matchPool.remove(this.user);
        }
    }
    private void startMatching(){
        log.info("start matching ....");
        matchPool.add(this.user);
        // 开始匹配
        while(matchPool.size() >= 2){
            Iterator<User> it = matchPool.iterator();
            User a = it.next(), b = it.next();
            matchPool.remove(a);
            matchPool.remove(b);

            JSONObject respA = new JSONObject();
            respA.put("event", "start_matching");
            respA.put("opponent_username", b.getDecUserName());
            respA.put("opponent_photo", b.getPhoto());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start_matching");
            respB.put("opponent_username", a.getDecUserName());
            respB.put("opponent_photo", a.getPhoto());
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }
    private void stopMatching(){
        log.info("stop matching ....");
        matchPool.remove(this.user);
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息

        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if(CommonConstant.START_MATCHING.equals(event)){
            startMatching();
        }else if(CommonConstant.STOP_MATCHING.equals(event)){
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    
    /**
     * @author fckey
     * @param message
     * @date 2022/10/19 17:30
     * @description 向前段发送信息
     * @return void
     */
    public void sendMessage(String message){
        synchronized (this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}