package com.community.dao;

import com.community.dataobject.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentExtMapper {
    //更新CommentCount
    int incCommentCount(Comment comment);

    int incLikeCount(Comment comment);
}
