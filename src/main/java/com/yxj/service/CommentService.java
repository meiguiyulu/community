package com.yxj.service;

import com.yxj.dto.CommentDTO;
import com.yxj.entity.Comment;
import com.yxj.entity.Notification;
import com.yxj.entity.Question;
import com.yxj.entity.User;
import com.yxj.enums.CommentTypeEnum;
import com.yxj.enums.NotificationStatusEnum;
import com.yxj.enums.NotificationTypeEnum;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
import com.yxj.mapper.CommentMapper;
import com.yxj.mapper.NotificationMapper;
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

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional // 事务
    public void insert(Comment comment, User commentator) {


        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(MyErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(MyErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
//            List<Comment> dbComments = commentMapper.selectByParentId(comment.getParentId());
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(MyErrorCode.COMMENT_NOT_FOUND);
            }

            Question question = questionMapper.getByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(MyErrorCode.QUESTION_NOT_FOUND);
            }

            // 添加评论数
            dbComment.setCommentCount(1);
            commentMapper.incCommentCount(dbComment);
            comment.setCommentCount(0);
            commentMapper.insert(comment);

            // 新增通知
            CreateNotify(comment, dbComment.getCommentator(), commentator.getName(),
                    question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

        } else {
            // 回复问题
            Question question = questionMapper.getByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            int updateStep = 1;
            questionMapper.incCommentCount(question.getId(), updateStep);
            comment.setCommentCount(0);
            commentMapper.insert(comment);

            // 新增通知
            CreateNotify(comment, question.getCreator(), commentator.getName(),
                    question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }

    }

    public List<CommentDTO> listByQuestionId(int id, CommentTypeEnum type) {

        List<Comment> comments = commentMapper.selectByParentIdAndType(id, type.getType());
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        List<CommentDTO> commentDTOS = new ArrayList<>();

        // 获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Object> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        for (Integer commentId : commentators) {
            List<Comment> commentList = commentMapper.selectByCommentatorId(commentId);
            User user = userMapper.findById(commentId);
            if (commentators.size() != 0) {
                for (Comment comment : commentList) {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(user);
                    commentDTOS.add(commentDTO);
                }
            }
        }

        return commentDTOS;
    }

    public List<CommentDTO> getByType(CommentTypeEnum comment) {
        List<Comment> commentList = commentMapper.selectByType(comment.getType());
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        List<CommentDTO> commentDTOList = new ArrayList<>();
        // 所有的评论者
        List<Integer> commentator = new ArrayList<>();
        for (Comment dbComment : commentList) {
            if (!commentator.contains(dbComment.getCommentator())) {
                commentator.add(dbComment.getCommentator());
            }
        }

        for (Integer useId : commentator) {
            User user = userMapper.findById(useId);
            List<Comment> comments = commentMapper.selectByCommentatorId(useId);
            if (comments.size() != 0) {
                for (Comment comment1 : commentList) {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment1, commentDTO);
                    commentDTO.setUser(user);
                    commentDTOList.add(commentDTO);
                }
            }
        }
        return commentDTOList;
    }

    public void CreateNotify(Comment comment, Integer receiver, String notifierName, String outerTitle,
                             NotificationTypeEnum notificationType, Integer outerId) {
        // 自己回复自己不需要通知
        if (receiver == comment.getCommentator()){
            return;
        }

        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);

        notificationMapper.insertNotification(notification);
    }

}
