package com.community.dao;

import com.community.dataobject.*;
import com.community.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.*;
@Mapper
public interface QuestionExtMapper {

    //相关分享select
    List<Question> selectRelated(Question question);

    //更新ViewCount
    int incView(Question record);

    //更新CommentCount
    int incCommentCount(Question record);

    //个人分享分页全查
    List<QuestionDTO> listWithPagination(Long userid, Integer offset, Integer size);

    //数数
    Integer countQuestion();

    //首页的分页
    List<QuestionDTO> selectByPagination(Integer offset, Integer size);

    //个人分享 自己有几个
    Integer countByUserId(Long userid);

    int incLikeCount(Question record);

    Integer countBySearch(QuestionQueryDTO record);

    List<QuestionDTO> selectBySearch(QuestionQueryDTO record);
}