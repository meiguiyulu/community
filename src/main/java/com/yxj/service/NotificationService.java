package com.yxj.service;

import com.yxj.dto.NotificationDTO;
import com.yxj.dto.PageDTO;
import com.yxj.entity.Notification;
import com.yxj.entity.User;
import com.yxj.enums.NotificationStatusEnum;
import com.yxj.enums.NotificationTypeEnum;
import com.yxj.exception.CustomizeException;
import com.yxj.exception.MyErrorCode;
import com.yxj.mapper.NotificationMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-26 12:51
 */

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO queryByUserId(Integer userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();
        Integer totalCount = notificationMapper.countByUseId(userId);
        pageDTO.setPagination(totalCount, page, size);

        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }

        int offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.queryByUserId(userId, offset, size);
        List<NotificationDTO> notificationList = new ArrayList<>();


        for (Notification notification: notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationList.add(notificationDTO);
        }

        pageDTO.setData(notificationList);

        return pageDTO;
    }

    public Integer unreadCount(Integer id) {
        return notificationMapper.countUnreadByReceiver(id);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification == null){
            throw new CustomizeException(MyErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver() != user.getId()){
            throw new CustomizeException(MyErrorCode.READ_NOTIFICATION_FAIL);
        }

        // 读了以后更新状态
        int status = NotificationStatusEnum.READ.getStatus();
        notificationMapper.updateNotificationStatus(id, status);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
