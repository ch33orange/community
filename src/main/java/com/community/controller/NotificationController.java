package com.community.controller;

import com.community.config.*;
import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.enums.*;
import com.community.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/notification/{id}")
    @NeedLogin
    public String profile(@PathVariable(name = "id") Long id, ModelMap modelMap,
                          HttpSession session) {

        User sessionAttribute = (User) session.getAttribute("user");
        if (sessionAttribute == null) {
            //用户为空就回去登录
            return "redirect:/";
        }
        //真正的找 不用session中的
        User user = userMapper.selectByPrimaryKey(sessionAttribute.getId().longValue());
        //把他读了
        NotificationDTO notificationDTO = notificationService.read(id, user);
        //
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}

