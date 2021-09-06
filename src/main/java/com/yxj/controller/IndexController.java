package com.yxj.controller;

import com.mysql.cj.util.StringUtils;
import com.yxj.cache.HotTagCache;
import com.yxj.dto.PageDTO;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-19 18:08
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @RequestMapping({"/", "/index"})
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag){

        List<String> tags = hotTagCache.getHots();
        model.addAttribute("tags", tags);

        PageDTO pageDTO = questionService.queryAll(search, tag, page, size);
        model.addAttribute("pageDTO", pageDTO);

        if (!StringUtils.isNullOrEmpty(search)){
            model.addAttribute("search", search);
        }
        if (!StringUtils.isNullOrEmpty(tag)){
            model.addAttribute("hotTag", tag);
        }
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
