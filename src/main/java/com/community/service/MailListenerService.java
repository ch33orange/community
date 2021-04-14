package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.enums.*;
import com.community.exception.*;
import com.community.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
/**
 * @email chivseg-hao@qq.com
 * @author:blue
 * @date: 2021/4/12
 * @time: 18:03
 */
@Service
@Slf4j
public class MailListenerService {

    @Autowired
    private MailService mailService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @RabbitListener(queues = MqConstant.MAIL_REGISTER_QUEUE)
    public void sendRegisterMail(Message message, Channel channel, Notification notification) throws IOException {
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        log.info(notificationDTO.getNotifierName()+notificationDTO.getTypeName()
                +"您的"+notificationDTO.getOuterTitle()+":[{}]", notification.getNotifierName());
        try {
            MailForm mailForm = new MailForm();
            User recevier = userMapper.selectByPrimaryKey(notification.getReceiver());
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("notifierName", notificationDTO.getNotifierName());
            userMap.put("typeName", notificationDTO.getTypeName());
            userMap.put("outerTitle", notificationDTO.getOuterTitle());
            userMap.put("id", notificationDTO.getId());
            userMap.put("recevierName", recevier.getName());
//            notificationDTO.getNotifierName()+notificationDTO.getTypeName()
//                    +"您的"+notificationDTO.getOuterTitle()+
//                    "点击链接访问 <a herf='121.196.52.244:8080/notification/"+notificationDTO.getId()+"'></a>"


            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            mailForm.setTo(Arrays.asList(
                    recevier.getEmail()))
                    .setSubject("码匠社区通知")
                    .setTemplateName("mailTemplate")//todo 模板引擎 之后搜一搜
                    .setContextVar(userMap);
            mailService.sendTemplateMail(mailForm);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("邮件发送成功");
        } catch (IOException e) {
            log.error("邮件发送失败", e.getMessage());
            // 回复消息处理失败，并重新入队
            // channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_QUEUE_SEND_ERROR);
        }
    }
}

