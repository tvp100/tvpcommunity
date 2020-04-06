package com.tvp100.community.dto;

import com.tvp100.community.mode.User;
import lombok.Data;

/**
 * Created by tvp100 on 2020/4/4.
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;//获得评论人的id，**XX**回复了标题
    private Long receiver;//创建问题的人或者是创建回复的人，我们要通知的人
    private Long outerid;//获得回复的问题的id(questionId)，用来查出标题  ----------有用
    private String type;//回复的是评论或者问题，存进数据库的是数字，这里拿出来的存进DTO的是文字（字符串）
    private Long gmtCreate;//回复时间  ----------有用
    private Integer status;//标记已读或者未读  ----------有用
    private String commentname;//评论人的名字  ----------有用
    private String title;//标题  ----------有用
}
