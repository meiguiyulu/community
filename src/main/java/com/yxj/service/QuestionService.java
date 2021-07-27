package com.yxj.service;

import com.mysql.cj.util.StringUtils;
import com.yxj.dto.PageDTO;
import com.yxj.dto.QuestionDTO;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
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

    public PageDTO queryAll(String search, Integer page, Integer size) {

        boolean isSearchEmpty = true;


        if (!StringUtils.isNullOrEmpty(search)){
            search = search.replace(' ', '|');
            isSearchEmpty = false;
        }

        PageDTO pageDTO = new PageDTO();
        Integer totalCount;

        if (isSearchEmpty){
            // 如果没有搜索
            totalCount = questionMapper.count();
        } else {
            // 如果有搜索
            totalCount = questionMapper.countBySearch(search);
        }

        pageDTO.setPagination(totalCount, page, size);


        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

        int offset = size * (page - 1);

        List<Question> questions;
        if (isSearchEmpty){
            // 如果没有搜索
            questions = questionMapper.queryAll(offset, size);
        } else {
            // 如果有搜索
            questions = questionMapper.queryAllBySearch(search, offset, size);
        }

        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question: questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setData(questionDTOList);
        return pageDTO;
    }

    public PageDTO queryByUserId(Integer userId, Integer page, Integer size) {
        PageDTO<QuestionDTO> pageDTO = new PageDTO<>();
        Integer totalCount = questionMapper.countByUseId(userId);
        pageDTO.setPagination(totalCount, page, size);

        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

        int offset = size * (page - 1);
        List<Question> questions = questionMapper.queryByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question: questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setData(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(int id) {
        Question question = questionMapper.getByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(MyErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == -1){
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(System.currentTimeMillis());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insertQuestion(question);
        } else {
            // 更新
            question.setGmtModify(System.currentTimeMillis());
            int updated = questionMapper.update(question);
            if (updated != 1){
                throw new CustomizeException(MyErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incrementViewCount(int id, int updateStep) {
        questionMapper.updateViewCount(id, updateStep);
    }

    public List<QuestionDTO> selectRelatedTag(QuestionDTO questionDTO) {
        if (StringUtils.isNullOrEmpty(questionDTO.getTag())){
            return new ArrayList<>();
        }

        String tags = questionDTO.getTag().replace(',', '|');

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(tags);


        List<Question> questions = questionMapper.selectRelatedTag(question);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question1: questions){
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(question1, questionDTO1);
            questionDTOList.add(questionDTO1);
        }

        return questionDTOList;
    }
}
