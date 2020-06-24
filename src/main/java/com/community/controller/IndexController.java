package com.community.controller;

import org.apache.commons.lang3.*;
import com.community.dto.*;
import com.community.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bluer on 2019/10/26
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    //主界面
    @RequestMapping("/")
//    @NeedLogin
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") String page,
                        @RequestParam(name = "size", defaultValue = "5") String size,
                        @RequestParam(name = "search",required = false) String search) {
        if (StringUtils.isBlank(search))
            search=null;
        //分页功能
        ///从数据库里找 工具類
        PaginationDTO pagination = questionService.selectByPagination(search,Integer.valueOf(page),
                Integer.valueOf(size));
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }

    //paperHelper插件写
}
