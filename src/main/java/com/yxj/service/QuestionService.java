package com.yxj.service;

import com.yxj.dto.PageDTO;
import com.yxj.dto.QuestionDTO;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.mapper.QuestionMapper;
import com.yxj.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-20 20:42
 */

@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO queryAll(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount, page, size);

        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

        int offset = size * (page - 1);
        List<Question> questions = questionMapper.queryAll(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question: questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setQuestions(questionDTOList);
//        pageDTO.setPage(page);

        return pageDTO;
    }
}
