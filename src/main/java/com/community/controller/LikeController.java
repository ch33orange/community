package com.community.controller;

import com.community.config.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.exception.*;
import com.community.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller  //点赞 想comment一样分评论和分享
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ResponseBody
    @PostMapping("/like")
//    @NeedLogin 需要进来给个错误2003
    public Object like(@RequestBody LikeDTO likeDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

//        魔改一下吧
        likeService.incLikeCount(likeDTO,user.getId(),user.getName());

        return ResultDTO.okOf();
    }
}
