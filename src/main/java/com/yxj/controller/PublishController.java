package com.yxj.controller;

import org.apache.commons.lang3.StringUtils;
import com.yxj.cache.TagCache;
import com.yxj.dto.QuestionDTO;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
//    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String doPublish(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "tag") String tag,
            @RequestParam(value = "id") String id,
            HttpServletRequest request,
            Model model){


        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

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

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
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
        if (StringUtils.isBlank(id)){
            question.setId(-1);
        } else {
            question.setId(Integer.valueOf(id));
        }

        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
