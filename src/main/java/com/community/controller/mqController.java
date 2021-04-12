package com.community.controller;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.exception.*;
import com.community.service.*;
import com.community.utils.*;
import com.google.code.kaptcha.*;
import com.google.code.kaptcha.impl.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * @email chivseg-hao@qq.com
 * @author:blue
 * @date: 2021/4/12
 * @time: 17:47
 */
@Controller
@Slf4j
public class mqController {

        @Autowired
        RabbitTemplate rabbitTemplate;

    @Autowired
    private CommentService commentService;

        @ResponseBody
        @RequestMapping(value = "/mqcomment", method = RequestMethod.POST)
        //    @NeedLogin  需要进来给个错误2003
        public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //回复是空的
        if(StringUtils.isBlank(commentCreateDTO.getContent())||commentCreateDTO ==null){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        Date date = new Date();
        comment.setGmtCreated(date);
        comment.setGmtModified(comment.getGmtCreated());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);

        commentService.insert(comment,user);
        return ResultDTO.okOf("操作成功,信息将邮件通知");
        }
}
