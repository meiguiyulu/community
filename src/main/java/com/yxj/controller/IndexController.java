package com.yxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LYJ
 * @create 2021-07-19 18:08
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String hello(){
        return "index";
    }
}
