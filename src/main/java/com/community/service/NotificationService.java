package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.enums.*;
import com.community.exception.*;
import lombok.extern.slf4j.*;
import org.apache.ibatis.session.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //给DTO附上值  获取分页总数
        Integer totalPage;

        NotificationExample notificationExample= new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer length = (int) notificationMapper.countByExample(notificationExample);

//        if(length<=0){   不需要报错了
//            throw new CustomizeException(CustomizeErrorCode.NO_PUBLISH);
//        }
        //判断页面数
        if (length % size == 0) {
            totalPage = length / size ;
        } else {
            totalPage = length / size + 1;
        }
        //        限制值范围
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        //size*9page-1)
        Integer offset = size * (page - 1);

//        分页取出通知我这位用户的回复 //最好加一个type已读之类的
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("gmt_created DESC");
        example.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if(notifications.size()==0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS =new ArrayList<>();

        for (Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            log.error("read notification NOTIFICATION_NOT_FOUND error.{}");
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }if (notification.getReceiver() != user.getId()){
            log.error("read notification not match READ_NOTIFICATION_FAIL error.{}");
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
//       把他read掉
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return  notificationDTO;
    }
}
