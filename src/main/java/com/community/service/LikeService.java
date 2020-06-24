package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.enums.*;
import com.community.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Slf4j
@Service   //点赞通知之后做
public class LikeService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void incLikeCount(LikeDTO likeDTO, Long followerId, String followerName) {

        if (likeDTO.getParentId() == 0 || likeDTO.getParentId() == null) {
            log.error("LikeService incLikeCount LIKE_TARGET_PARAM_NOT_FOUND error.{}");
            throw new CustomizeException(CustomizeErrorCode.LIKE_TARGET_PARAM_NOT_FOUND);
        }
        if (likeDTO.getType() == null || !CommentTypeEnum.isExist(likeDTO.getType())) {
            log.error("LikeService incLikeCount  LIKE_TYPE_PARAM_WRONG error.{}");
            throw new CustomizeException(CustomizeErrorCode.LIKE_TYPE_PARAM_WRONG);
        }
        if (likeDTO.getType() == CommentTypeEnum.COMMENT.getType()) {
            //点赞评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(likeDTO.getParentId());
            if (dbcomment == null) {
                log.error("LikeService incLikeCount like for comment  COMMENT_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //先取分享
            Question dbquestion = questionMapper.selectByPrimaryKey(dbcomment.getParentId());
            if (dbquestion == null) {
                log.error("LikeService incLikeCount like for comment QUESTION_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            if (followerId == dbcomment.getCommentator()){
                return ;
            }
            //增加点赞数
            dbcomment.setLikeCount(1);
            commentExtMapper.incLikeCount(dbcomment);

            //创建通知      谁点赞(id) 通知接受者人的id  谁点赞(名字)  被点赞发布标题 通知类型  点赞什么outer它的parentId
            createNotify(followerId, dbcomment.getCommentator(), followerName, dbquestion.getTitle(),
                    NotificationTypeEnum.LIKE_COMMENT.getType(), dbcomment.getId());
        } else {
            //点赞分享
            Question dbquestion = questionMapper.selectByPrimaryKey(likeDTO.getParentId());
            if (dbquestion == null) {
                log.error("LikeService incLikeCount like for question QUESTION_NOT_FOUND error.{}");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //自己给自己点不行
            if (followerId == dbquestion.getCreator()){
                return ;
            }
            //增加点赞数
            dbquestion.setLikeCount(1);
            questionExtMapper.incLikeCount(dbquestion);
            //创建通知      谁点赞(id) 通知接受者人的id  谁点赞(名字)  被点赞发布标题 通知类型  点赞什么outer它的parentId
            createNotify(followerId, dbquestion.getCreator(), followerName, dbquestion.getTitle(),
                    NotificationTypeEnum.LIKE_QUESTION.getType(), dbquestion.getId());
        }
    }

    //创建通知
    private void createNotify(Long follwerId, Long receiver, String nofitierName, String outerTitle,
                              int notificationType,
                              Long outerId) {
//        这里判断不到点赞者和评论者相同
        //设置通知
        Notification notification = new Notification();
        notification.setGmtCreated(new Date());
        notification.setType(notificationType);
//        回复的是哪一条
        notification.setOuterId(outerId);
//            是谁填的这条回复
        notification.setNotifier(follwerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
//            这条回复通知谁
        notification.setReceiver(receiver);
        notification.setNotifierName(nofitierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

}
