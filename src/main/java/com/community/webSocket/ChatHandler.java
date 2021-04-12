package com.community.webSocket;
/**
 * @Copyright 源码阅读网 http://coderead.cn
 */

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * 聊天室
 *
 * @author 鲁班大叔
 * @date 2020/11/5 10:36 AM
 */
//@Component
@RequestMapping
public class ChatHandler extends TextWebSocketHandler {
    @RequestMapping("/room")
    public String open() {
        return "chart";
    }

    Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println(message.getPayload());

        for (WebSocketSession online : users.values()) {
            User user = (User) session.getAttributes().get("user");
            TextMessage msg = buildMessage("msg", new Message(user, message.getPayload()));
            try {
                online.sendMessage(msg);
            } catch (IOException e) {
                System.out.println("发送失败：" + online);
                e.printStackTrace();
            }
        }
    }

    //关闭连接
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        System.out.println("退出" + session);
        if (session.getAttributes().containsKey("user")) {
            User user = (User) session.getAttributes().get("user");
            users.remove(user.name);
            updateUsers();
        }

    }

    static String[] heads = {"elliot.jpg  ",
            "helen.jpg   ",
            "jenny.jpg   ",
            "steve.jpg   ",
            "stevie.jpg  ",
            "veronika.jpg"};

    // 建立连接
    // ws://127.0.0.1:8080?luban
    // 登陆 初始化绑定用户、 更新用户列表--》所有用户
    //
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        System.out.println("加入" + session);
        String name = session.getUri().getQuery();
        Assert.hasText(name, "用户不能为空");
        if (!users.containsKey(name)) {
            users.put(name, session);
            String head = heads[new Random().nextInt(6)];
            session.getAttributes().put("user", new User(name, "/images/" + head));
            updateUsers();
        } else {
            session.sendMessage(buildMessage("error_exist", "用户名已存在，请刷新重试"));
        }

    }

    // 更新在线用户列表
        private void updateUsers() throws IOException {
        List<Object> user = users.values().stream().map(s -> s.getAttributes().get("user")).collect(Collectors.toList());
        TextMessage updateUsers = buildMessage("users", user);
        for (WebSocketSession online : this.users.values()) {
            online.sendMessage(updateUsers);
        }
    }


    TextMessage buildMessage(String type, Object content) {
        ObjectMapper m = new ObjectMapper();
        Map map = new HashMap();
        map.put("type", type);
        map.put("content", content);
        String json = null;
        try {
            json = m.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        TextMessage result = new TextMessage(json);
        return result;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("异常：" + session);
        exception.printStackTrace();
    }

    public static class User implements java.io.Serializable {
        public User(String name, String head) {
            this.name = name;
            this.head = head;
        }

        public String name;
        public String head;// 头象
    }

    public static void main(String[] args) throws JsonProcessingException {
        for (int i = 0; i < 1000; i++) {

            System.out.println(heads[new Random().nextInt(6)]);
        }

    }

    public static class Message implements java.io.Serializable {
        public Message(User user, String msg) {
            this.user = user;
            this.msg = msg;
            this.time = new SimpleDateFormat("hh:mm").format(new Date());
        }

        public User user; // 发送者
        public String msg; // 消息内容
        public String time;// 发送时间


    }
}
