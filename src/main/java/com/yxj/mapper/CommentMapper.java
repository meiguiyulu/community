package com.yxj.mapper;

import com.yxj.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-22 21:15
 */

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id, type, commentator, gmt_create, gmt_modify, like_count, content, comment_count)" +
            "values(#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModify}, #{likeCount}, #{content}, #{commentCount})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parentId}")
    List<Comment> selectByParentId(int parentId);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> selectByParentIdAndType(int id, Integer type);

    @Select("select * from comment where commentator = #{commentatorId} order by gmt_create desc")
    List<Comment> selectByCommentatorId(Integer commentatorId);

    @Select("select * from comment where id = #{id}")
    Comment selectByPrimaryKey(Integer id);

    @Update("update comment set comment_count = comment_count + #{commentCount} " +
            "where id = #{id}")
    void incCommentCount(Comment dbComment);

    @Select("select * from comment where type = #{type}")
    List<Comment> selectByType(Integer type);
}
