package com.yxj.controller;

import com.yxj.dto.PageDTO;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LYJ
 * @create 2021-07-19 18:08
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping({"/", "/index"})
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size){

        PageDTO pageDTO = questionService.queryAll(page, size);
        model.addAttribute("pageDTO", pageDTO);

        return "index";
    }
}
