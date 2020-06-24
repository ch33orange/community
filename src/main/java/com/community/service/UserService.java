package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * 2019/11/19   基本没用 直接用userExtMapper更快
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private UserMapper userMapper;

    public void create(User user) {
        User dbuer = userExtMapper.selectByName(user.getName());
        if (dbuer == null) {
            //插入
            user.setGmtCreated(new Date());
            user.setGmtModified(user.getGmtCreated());
            userMapper.insert(user);
        } else {
            log.error("UserService create User repeat error.{}");
            throw new CustomizeException(CustomizeErrorCode.USER_ALREADY_EXIST);
        }
    }

    //更新修改头像用的
    public void update(User user) {
        //更新
        userMapper.updateByPrimaryKeySelective(user);
    }
}

