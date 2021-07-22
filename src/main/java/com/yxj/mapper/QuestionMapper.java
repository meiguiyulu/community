package com.yxj.mapper;

import com.yxj.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-20 15:51
 */

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title, description, gmt_create, gmt_modify, creator, comment_count, view_count, like_count, tag) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModify}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tag})")
    void insertQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> queryAll(int offset, Integer size);

    @Select("select count(1) from question")
    int count();

    @Select("select * from question where creator = ${userId} limit #{offset},#{size}")
    List<Question> queryByUserId(Integer userId, int offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUseId(Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(int id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modify=#{gmtModify}," +
            "tag=#{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    void updateViewCount(int id);
}
