package com.yxj.controller;

import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.mapper.QuestionMapper;
import com.yxj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LYJ
 * @create 2021-07-20 15:03
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
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


        Cookie[] cookies = request.getCookies();
        User user = null;

        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie: cookies) {
                if ("token".equals(cookie.getName())){
                    String value = cookie.getValue();
                    user = userMapper.fingByToken(value);
                    if (user!=null){
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }

        if (user==null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModify(question.getGmtCreate());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
