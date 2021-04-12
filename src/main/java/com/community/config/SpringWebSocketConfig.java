//package com.community.config;
//
///**
// * @email chivseg-hao@qq.com
// * @author:ch33orange
// * @date: 2020/11/8
// * @time: 16:52
// */
//import com.community.webSocket.*;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.*;
//
//
//@Configuration
//@EnableWebSocket
//public class SpringWebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(wsHandler(), "/ws");
//        registry.addHandler(chatHandler(), "/chat");
//    }
//
//    @Bean
//    public TextWebSocketHandler wsHandler() {
//        return new WsHandler();
//    }
//
//    @Bean
//    public TextWebSocketHandler chatHandler() {
//        return new ChatHandler();
//    }
//
//}