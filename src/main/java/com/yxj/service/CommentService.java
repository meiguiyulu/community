package com.yxj.service;

import com.yxj.dto.CommentDTO;
import com.yxj.entity.Comment;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.enums.CommentTypeEnum;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
import com.yxj.mapper.CommentMapper;
import com.yxj.mapper.QuestionMapper;
import com.yxj.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LYJ
 * @create 2021-07-23 9:25
 */

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional // 事务
    public void insert(Comment comment) {


        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(MyErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(MyErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            List<Comment> dbComments = commentMapper.selectByParentId(comment.getParentId());
            if (dbComments == null){
                throw new CustomizeException(MyErrorCode.COMMENT_NOT_FOUND);
            }

        } else {
            // 回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            int updateStep = 1;
            questionMapper.incCommentCount(question.getId(), updateStep);
        }
        commentMapper.insert(comment);
    }

    public List<CommentDTO> listByQuestionId(int id, CommentTypeEnum type) {


        List<Comment> comments = commentMapper.selectByParentIdAndType(id, type.getType());
        if (comments.size() == 0){
            return new ArrayList<>();
        }

        List<CommentDTO> commentDTOS = new ArrayList<>();

        // 获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Object> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        for (Integer commentId: commentators){
            List<Comment> commentList =  commentMapper.selectByCommentatorId(commentId);
            User user = userMapper.findById(commentId);
            if (commentators.size()!=0){
                for (Comment comment: commentList){
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(user);
                    commentDTOS.add(commentDTO);
                }
            }
        }

        return commentDTOS;
    }
}
