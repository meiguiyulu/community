package com.yxj.interceptor;

import com.yxj.entity.User;
import com.yxj.mapper.NotificationMapper;
import com.yxj.mapper.UserMapper;
import com.yxj.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LYJ
 * @create 2021-07-21 20:31
 */

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie: cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.fingByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user", user);
                        // 查询未读的数量
                        Integer unreadCount = notificationService.unreadCount(user.getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
