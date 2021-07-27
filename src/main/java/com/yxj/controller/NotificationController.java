package com.yxj.controller;

import com.yxj.dto.NotificationDTO;
import com.yxj.dto.PageDTO;
import com.yxj.entity.User;
import com.yxj.enums.NotificationTypeEnum;
import com.yxj.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.PriorityQueue;

/**
 * @author LYJ
 * @create 2021-07-26 16:06
 */

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
