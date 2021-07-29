package com.yxj.mapper;

import com.yxj.entity.User;
import org.apache.ibatis.annotations.*;


/**
 * @author LYJ
 * @create 2021-07-19 22:15
 */

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, gmt_create, gmt_modify, avatar_url, type) " +
            "values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModify}, #{avatarUrl}, #{type})")
    void insertUser(User user);


    @Select("select * from user where token = #{token}")
    public User fingByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);

    @Select("select * from user where account_id = #{accountId} and type = #{type}")
    User findByAccountIdAndType(String accountId, String type);

    @Update("update user set gmt_modify=#{gmtModify}, avatar_url=#{avatarUrl}," +
            "name=#{name}, token=#{token} where account_id = #{accountId}")
    void update(User user);
}
