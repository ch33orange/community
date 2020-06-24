package com.community.dao;

import com.community.dataobject.*;
import org.apache.ibatis.annotations.*;

import java.util.*;


@Mapper
public interface UserExtMapper {
    //人名查找
    User selectByName(String login);

    //手机号查找
    User selectByMobile(String mobile);

    //邮箱查找
    User selectByEmail(String email);

    //数总数
    Integer countUsers();

}