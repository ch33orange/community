package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.enums.*;
import com.community.exception.*;
import com.community.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //新增恢复 用上了事务
    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == 0 || comment.getParentId() == null) {
            log.error("CommentService  TARGET_PARAM_NOT_FOUND error.{}");
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            log.error("CommentService   TYPE_PARAM_WRONG error.{}");
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                log.error("CommentService comment for comment COMMENT_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //先取分享
            Question dbquestion = questionMapper.selectByPrimaryKey(dbcomment.getParentId());
            if (dbquestion == null) {
                log.error("CommentService comment for comment QUESTION_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //增加评论数
            dbcomment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbcomment);

            //创建通知
            createNotify(comment, dbcomment.getCommentator(), commentator.getName(), dbquestion.getTitle(),
                    NotificationTypeEnum.REPLY_COMMENT.getType(), dbquestion.getId());
        } else {
            //回复分享
            Question dbquestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbquestion == null) {
                log.error("CommentService comment for question QUESTION_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
//            增加评论数
            dbquestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbquestion);
            //创建通知
            createNotify(comment, dbquestion.getCreator(), commentator.getName(), dbquestion.getTitle(),
                    NotificationTypeEnum.REPLY_QUESTION.getType(), dbquestion.getId());

        }
    }

    //创建通知
    private void createNotify(Comment comment, Long receiver, String nofitierName, String outerTitle, int notificationType, Long outerId) {
        if(receiver.equals(comment.getCommentator())){
            return;
        }
        //设置通知
        Notification notification = new Notification();
        notification.setGmtCreated(new Date());
        notification.setType(notificationType);
//        回复的是哪一条
        notification.setOuterId(outerId);
//            是谁填的这条回复
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
//            这条回复通知谁
        notification.setReceiver(receiver);
        notification.setNotifierName(nofitierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
        // 邮件通知
        rabbitTemplate.convertAndSend(MqConstant.MAIL_REGISTER_EXCHANGE,MqConstant.MAIL_REGISTER_ROUTING_KEY,notification);
    }

    //回复列表  用example了 据说正式的话不能用不管咯 列出来
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("`like_count` DESC,gmt_modified DESC");
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //使用lambda表达式获取去重的评论人
        Set<Long> commentors = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentors);

        // 获取评论人并转换为 ,Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换comment 为 commentDTO
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());


        return commentDTOs;
    }
}
