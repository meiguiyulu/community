package com.yxj.controller;

import com.yxj.dto.PageDTO;
import com.yxj.entity.User;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LYJ
 * @create 2021-07-21 16:12
 */

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PageDTO pageDTO = questionService.queryByUserId(user.getId(), page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "profile";
    }

}
