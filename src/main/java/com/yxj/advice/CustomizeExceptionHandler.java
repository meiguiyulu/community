package com.yxj.advice;

import com.alibaba.fastjson.JSON;
import com.yxj.dto.ResultDTO;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LYJ
 * @create 2021-07-22 16:21
 */


@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model,
                        HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            // 返回JSON
            ResultDTO resultDTO;
            if (e instanceof CustomizeException){
                resultDTO =  ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO =  ResultDTO.errorOf(MyErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        } else {
            // 错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("msg", ((CustomizeException) e).getMsg());
            } else {
//                model.addAttribute("msg", MyErrorCode.SYS_ERROR.getMessage());
                model.addAttribute("msg", "乱七八糟");
            }
            return new ModelAndView("error");
        }
    }
}
