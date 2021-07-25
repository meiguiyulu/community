package com.yxj.mapper;

import com.yxj.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-22 21:15
 */

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id, type, commentator, gmt_create, gmt_modify, like_count, content)" +
            "values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModify}, #{likeCount}, #{content})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    List<Comment> selectByParentId(int parentId);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> selectByParentIdAndType(int id, Integer type);

    @Select("select * from comment where commentator = #{commentId} order by gmt_create desc")
    List<Comment> selectByCommentatorId(Integer commentId);
}
