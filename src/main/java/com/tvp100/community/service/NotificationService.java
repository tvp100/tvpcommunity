package com.tvp100.community.service;

import com.tvp100.community.dto.NotificationDTO;
import com.tvp100.community.dto.PaginationDTO;
import com.tvp100.community.dto.QuestionDTO;
import com.tvp100.community.enums.NotificationTypeEnum;
import com.tvp100.community.exception.CustomizeErrorCode;
import com.tvp100.community.exception.CustomizeException;
import com.tvp100.community.mapper.NotificationMapper;
import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tvp100 on 2020/4/4.
 */
@Service
public class NotificationService {

    @Autowired(required = false)
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        paginationDTO.setPagination(totalCount,page,size);
        //这是放进来关于page的判断————————————
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        //————————————————————————
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        //分页查出Notification表中的所有数据，包含时间数据,要通知的人
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size()==0){
            return paginationDTO;
        }
        //封装数据
        List<NotificationDTO> notificationDTOlist = new ArrayList<>();
        notificationDTOlist = notifications.stream().map(ntify->{
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setCommentname(ntify.getCommentName());
            notificationDTO.setGmtCreate(ntify.getGmtCreate());
            notificationDTO.setTitle(ntify.getTitle());
            notificationDTO.setOuterid(ntify.getOuterid());
            notificationDTO.setStatus(ntify.getStatus());
            notificationDTO.setId(ntify.getId());
            if (ntify.getType()==1){
                notificationDTO.setType(NotificationTypeEnum.REPLAY_QUESTION.getName());
            }else {
                notificationDTO.setType(NotificationTypeEnum.REPLAY_COMMENT.getName());
            }
            return notificationDTO;
        }).collect(Collectors.toList());
        paginationDTO.setData(notificationDTOlist);
        return paginationDTO;
    }

    public Long GetUnreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(0);
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //复制数据，拿到outerId，即quesion的Id
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        //标记已读
        notification.setStatus(1);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andIdEqualTo(notification.getId());
        notificationMapper.updateByExampleSelective(notification,notificationExample);
        return notificationDTO;
    }
}
