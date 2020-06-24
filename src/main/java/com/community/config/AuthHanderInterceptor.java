package com.community.config;


import com.community.dataobject.*;
import com.community.dao.UserMapper;
import com.community.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.*;
import java.util.*;

/**
 * @author blue
 * @date 2019-9-26
 */
@Component
public class AuthHanderInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean flag = true;
        //如果我执行的handler方法属于 method  类型转换一下
        if (handler instanceof HandlerMethod) {
            //取出method的注解看看有没有加上注解 在needlogin上
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NeedLogin needLogin = handlerMethod.getMethodAnnotation(NeedLogin.class);
            //注解有 就要做用户登录判断 从session中取出用户信息 然后再登录
            if (needLogin != null) {

                //查有没有cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length != 0) {
                    //查银行卡
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("bluer_userID")) {
                            String id = cookie.getValue();
                            User member = userMapper.selectByPrimaryKey(Long.parseLong(id));
                            //账户也没注销
                            if (member != null && request.getSession().getAttribute("user") != null)
                            {
                                //说明有用户
                                //同时把通知给拿到
                                Long unreadCount = notificationService.unreadCount(member.getId());
                                request.getSession().setAttribute("user", member);
                                request.getSession().setAttribute("unreadCount",unreadCount);
                                return true;
                            }
                        }
                    }
                }
                //没银行卡
                response.sendRedirect("/login");
                flag = false;

            }
        }
        return flag;
    }
}
