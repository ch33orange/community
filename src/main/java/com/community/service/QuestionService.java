package com.community.service;

import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.exception.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * 2019/11/01
 */
@Service
@Slf4j
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    //新功能  用户中心的我的分享
    public PaginationDTO listWithPagination(Long userid, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        //给DTO附上值  获取分页总数
        Integer totalPage;
        Integer totalCount = questionExtMapper.countByUserId(userid);

        if(totalCount<0){
            throw new CustomizeException(CustomizeErrorCode.NO_PUBLISH);
        }
        //判断页面数
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
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
        List<QuestionDTO> list = questionExtMapper.listWithPagination(userid, offset, size);
        paginationDTO.setData(list);
        return paginationDTO;
    }


    //启用  首页
    public PaginationDTO selectByPagination(String search, Integer page, Integer size) {

        if(StringUtils.isNotBlank(search)){
//            regexTag = search.replace(" ","|");
            //        | 替代 , 自己实现吧
        String[] tags = StringUtils.split(search, " ");
        search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();
        //给DTO附上值  获取分页总数
        Integer totalPage;
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        //这里还是要数据库弄的 就是怕后面controller层自己弄和数据库查出来了不匹配
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        if(totalCount<0){
            throw new CustomizeException(CustomizeErrorCode.NO_PUBLISH);
        }
        //判断页面数
        if (totalCount % size != 0) {
            totalPage = totalCount / size + 1;
        } else {
            totalPage = totalCount / size;
        }
        //        限制值范围
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);

        List<QuestionDTO> list = questionExtMapper.selectBySearch(questionQueryDTO);
        //search为空返回全部
        paginationDTO.setData(list);
        return paginationDTO;
    }


//        //字符串判断在controller做比较好的话可以减轻数据库压力
//        // 但是分页就要重新写了所以就弃用了
//        List<QuestionDTO> list = questionExtMapper.selectByPagination(offset,size);
//        if(StringUtils.isNotBlank(search)){
//            //不区分大小写
//            Pattern pattern = Pattern.compile(search,Pattern.CASE_INSENSITIVE);
//            List<QuestionDTO> realList = new ArrayList<>();
//            for (QuestionDTO questionDTO : list) {
//                Matcher matcher =pattern.matcher(questionDTO.getTitle());
//                if (matcher.find()){
//                    realList.add(questionDTO);
//                }
//                //可能要的重新考虑一下排序了
//            }
//            paginationDTO.setData(realList);
//            return paginationDTO;
//        }


    //自己的分享详情页 根据id得到
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreator().longValue());
        questionDTO.setBio(user.getBio());
        questionDTO.setName(user.getName());
        questionDTO.setEmail(user.getEmail());
        questionDTO.setIcon(user.getIcon());
        questionDTO.setMobile(user.getMobile());
        questionDTO.setQq(user.getQq());
        return questionDTO;
    }

    public void createOrUpdate(QuestionDTO questionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        Date date = new Date();
        if (question.getId() == null) {
            //创建
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setGmtCreated(date);
            question.setGmtModify(date);
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModify(date);
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if (updated != 1) {
                log.error("update question QUESTION_NOT_FOUND error.{} ");
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionExtMapper.incView(updateQuestion);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
//        | 替代 , 自己实现吧
        String regexTag = queryDTO.getTag().replace(",","|");
//        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
//        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));

        Question question =new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> qeustionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return qeustionDTOS;
    }
}
