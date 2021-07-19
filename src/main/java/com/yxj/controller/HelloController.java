package com.yxj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LYJ
 * @create 2021-07-19 18:08
 */

@RestController
public class HelloController {

    @RequestMapping("/")
    public String hello(){
        return "Hello World!";
    }
}
