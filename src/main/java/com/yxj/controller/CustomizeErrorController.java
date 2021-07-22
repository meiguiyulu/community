package com.yxj.controller;

import org.springframework.boot.Banner;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LYJ
 * @create 2021-07-22 17:26
 */

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    public ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status = getStatus(request);

        if (status.is4xxClientError()){
            model.addAttribute("msg", "你这个请求错误, 换一个请求吧~");
        }
        if (status.is5xxServerError()){
            model.addAttribute("msg", "哎呀呀, 服务器冒烟了, 过一会再试试吧~");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
