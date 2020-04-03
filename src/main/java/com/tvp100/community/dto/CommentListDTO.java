package com.tvp100.community.dto;

import com.tvp100.community.mode.User;
import lombok.Data;

/**
 * Created by tvp100 on 2020/4/1.
 */
@Data
public class CommentListDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Long commentCount;
    private User user;
}
