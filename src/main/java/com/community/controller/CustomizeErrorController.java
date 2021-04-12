package com.community.controller;


import lombok.extern.slf4j.*;
import org.springframework.boot.web.servlet.error.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;
/**
 * 2019/11/19
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}") //为什么爆红????
//@RequestMapping("/error")
@Slf4j

//              请注意,是因为你用了tomcat才不能进入报错页面 别乱弄tomcat进来
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE,value = "/error")
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()) {
            log.error("CustomizeErrorController errorHtml request 4xx error.{}");
            model.addAttribute("message","你的请求出错,宁要不然换一个姿势?");
        }
        if (status.is5xxServerError()) {
            log.error("CustomizeErrorController errorHtml request 5xx error.{}");
            model.addAttribute("message","服务器冒烟了,宁要不然稍后再试试~");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            log.error("CustomizeErrorController getStatus cannot return status error.{}");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
