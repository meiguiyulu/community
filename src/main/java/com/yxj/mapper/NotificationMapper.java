package com.yxj.mapper;

import com.yxj.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-26 9:54
 */

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(notifier, receiver, outer_id, type, gmt_create, status, notifier_name, outer_title) " +
            "values(#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insertNotification(Notification notification);

    @Select("select count(1) from notification where receiver = #{userId}")
    Integer countByUseId(Integer userId);

    @Select("select * from notification where receiver = #{userId} order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> queryByUserId(Integer userId, int offset, Integer size);

    @Select("select count(1) from notification where receiver = #{id} and status = 0")
    Integer countUnreadByReceiver(Integer id);

    @Select("select * from notification where id = #{id}")
    Notification selectByPrimaryKey(Integer id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateNotificationStatus(Integer id, int status);
}
