package com.community.controller;

import com.community.cache.*;
import com.community.config.*;
import com.community.dataobject.*;
import com.community.dto.*;
import org.apache.commons.lang3.*;
import com.community.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;
import javax.validation.*;
import javax.validation.constraints.*;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    //更新
    @GetMapping("/publish/{id}")
    @NeedLogin
//    @ModelAttribute("question") QuestionDTO questionDTO,
    public ModelAndView edit(@ModelAttribute("invalid") String invalid, Model model, @PathVariable(name = "id") Long id) {
        ModelAndView view = new ModelAndView();
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        view.addObject(BindingResult.class.getName() + ".question",
                model.asMap().get("questionError"));
        view.setViewName("/publish");
        view.addObject("cacheTags", Tagcache.get());
        return view;
    }


    //发布页面
    @GetMapping("/publish")
    @NeedLogin
    public ModelAndView pulish(@ModelAttribute("question") QuestionDTO questionDTO,
                               @ModelAttribute("invalid") String invalid,
                               Model model) {
        ModelAndView view = new ModelAndView();
        view.addObject(BindingResult.class.getName() + ".question",
                model.asMap().get("questionError"));
        view.setViewName("/publish");
        if("请勿输入非法标签:".equals(invalid)){
            view.addObject("invalid",null);
        }
        view.addObject("cacheTags", Tagcache.get());
        return view;
    }

    @PostMapping("/publish")
    @NeedLogin  //这样就严格了  后期像回复那边一样处理一下吧
    public RedirectView dopublish(HttpServletRequest request, @Valid QuestionDTO questionDTO,
                                  BindingResult result, RedirectAttributes attributes) {
        RedirectView view = new RedirectView();
        if (result.hasErrors()) {
            attributes.addFlashAttribute("question", questionDTO);
            attributes.addFlashAttribute("questionError", result);
            view.setUrl("/publish");
            return view;
        }
        //上面肯定了不为空
        String invalid = Tagcache.filterInvalid(questionDTO.getTag());
        if (StringUtils.isNotBlank(invalid)) {
            attributes.addFlashAttribute("question", questionDTO);
            attributes.addFlashAttribute("questionError", result);
            attributes.addFlashAttribute("invalid", "请勿输入非法标签:" + invalid);
            view.setUrl("/publish");
            return view;
        }
        //从session中强行拿用户
        User user = (User) request.getSession().getAttribute("user");
        questionDTO.setCreator(user.getId());
        questionService.createOrUpdate(questionDTO);
        view.setUrl("/profile/questions");
        return view;
    }

}