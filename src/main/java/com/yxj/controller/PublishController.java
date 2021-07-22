package com.yxj.controller;

import com.yxj.dto.QuestionDTO;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

/**
 * @author LYJ
 * @create 2021-07-20 15:03
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") int id,
                       Model model){

        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            @RequestParam(value = "id") int id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title==null || "".equals(title)){
            model.addAttribute("error", "标题不能为空");
            return "/publish";
        }
        if (description==null || "".equals(description)){
            model.addAttribute("error", "内容不能为空");
            return "/publish";
        }
        if (tag==null || "".equals(tag)){
            model.addAttribute("error", "标签不能为空");
            return "/publish";
        }


        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        // id可能为空
        question.setId(id);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
