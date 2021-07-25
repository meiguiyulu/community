package com.yxj.controller;

import com.yxj.dto.CommentDTO;
import com.yxj.dto.QuestionDTO;
import com.yxj.enums.CommentTypeEnum;
import com.yxj.service.CommentService;
import com.yxj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-21 21:18
 */

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") int id, Model model) {
        // 累加阅读数
        int updateStep = 1;
        questionService.incrementViewCount(id, updateStep);
        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);

        List<CommentDTO> commentTypeComment = commentService.getByType(CommentTypeEnum.COMMENT);

        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("commentTypeComments", commentTypeComment);
        System.out.println("QuestionController===============>question");
        System.out.println(commentTypeComment);
        return "question";
    }

}
