package com.yxj.mapper;

import com.yxj.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author LYJ
 * @create 2021-07-19 22:15
 */

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id, name, token, gmt_create, gmt_modify) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModify})")
    void insertUser(User user);
}
