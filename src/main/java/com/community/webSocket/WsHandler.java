package com.community.webSocket;
/**
 * @Copyright 源码阅读网 http://coderead.cn
 */

import org.springframework.stereotype.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

/**
 * @author 鲁班大叔
 * @date 2020/11/7 10:15 AM
 */

/**
 * 客户端的使用
 *  var ws=new WebSocket("ws://127.0.0.1:8080/ws"); 升级协议，不能直接访问，必须进行过http 通信之后
 *  ws.onmessage= 接收消息
 *  ws.onopen=  打开连接
 *  ws.onerror= 处理异常
 *  ws.onclose= 关闭连接
 */
//@Component
public class WsHandler extends TextWebSocketHandler {
    // 只能处理文本消息
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.getPayload());
        session.sendMessage(message);
    }
}
