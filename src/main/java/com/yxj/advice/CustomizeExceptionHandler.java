package com.yxj.advice;

import com.yxj.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LYJ
 * @create 2021-07-22 16:21
 */

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model){

        if (e instanceof CustomizeException){
            model.addAttribute("msg", ((CustomizeException) e).getMsg());
        } else {
            model.addAttribute("msg", "服务冒烟了,要不稍后再试试吧!");
        }

        return new ModelAndView("error");
    }
}
