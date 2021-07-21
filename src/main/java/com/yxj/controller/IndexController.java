package com.yxj.controller;

import com.yxj.dto.PageDTO;
import com.yxj.dto.QuestionDTO;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.mapper.QuestionMapper;
import com.yxj.mapper.UserMapper;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-19 18:08
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping({"/", "/index"})
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie: cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.fingByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        PageDTO pageDTO = questionService.queryAll(page, size);
        model.addAttribute("pageDTO", pageDTO);

        return "index";
    }
}
