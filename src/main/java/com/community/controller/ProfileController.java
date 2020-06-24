package com.community.controller;

import com.community.Provider.*;
import com.community.config.*;
import com.community.dao.*;
import com.community.dataobject.*;
import com.community.dto.*;
import com.community.service.*;
import com.community.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;
import javax.validation.*;

@Controller
@Slf4j
public class    ProfileController {

    public static final String SOLT = "Isitapublicpwd";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UCloudProvider ucloundProvider;

    @GetMapping("/profile/{action}")
    @NeedLogin
    public String profile(@PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpSession session){

        User sessionAttribute = (User)session.getAttribute("user");
        if(sessionAttribute==null){
            //用户为空就回去登录
            return "redirect:/";
        }
        //真正的找 不用session中的
        User user =  userMapper.selectByPrimaryKey(sessionAttribute.getId().longValue());
        if ("details".equals(action)){
            model.addAttribute("section","details");
            model.addAttribute("sectionName","修改头像");
        }
        else if("questions".equals(action)){
            PaginationDTO paginationDTO = questionService.listWithPagination(user.getId(),
                    page,size);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的发布");
            model.addAttribute("pagination", paginationDTO);
        }
        else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pagination", paginationDTO);
        }

        return "profile";
    }

    @PostMapping("/profile/details")
    @NeedLogin
    public RedirectView details(HttpServletRequest request,@RequestParam("icon") String icon){
        RedirectView view =new RedirectView();
        User user = (User) request.getSession().getAttribute("user");
        user.setIcon(icon);
        userService.update(user);
        view.setUrl("/");
        return view;
    }
}
