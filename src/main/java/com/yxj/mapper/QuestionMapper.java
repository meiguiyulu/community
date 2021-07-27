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

    @Select("select * from question order by gmt_create desc limit #{offset}, #{size}")
    List<Question> queryAll(int offset, Integer size);

    @Select("select count(1) from question")
    int count();

    @Select("select * from question where creator = ${userId} order by gmt_create desc limit #{offset},c#{size}")
    List<Question> queryByUserId(Integer userId, int offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUseId(Integer userId);

    @Select("select * from question where id = #{id}")
    Question getByPrimaryKey(int id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modify=#{gmtModify}," +
            "tag=#{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = view_count + #{updateStep} where id = #{id}")
    void updateViewCount(int id, int updateStep);

    @Update("update question set comment_count = comment_count + #{updateStep} where id = #{id}")
    void incCommentCount(Integer id, int updateStep);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectRelatedTag(Question question);

    @Select("select count(1) from question where title regexp #{search}")
    Integer countBySearch(String search);

    @Select("select * from question where title regexp #{search} order by gmt_create desc limit #{offset}, #{size}")
    List<Question> queryAllBySearch(String search, int offset, Integer size);
}
