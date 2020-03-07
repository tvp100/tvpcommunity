package com.tvp100.community.dto;

import com.tvp100.community.mode.User;
import lombok.Data;

/**
 * Created by tvp100 on 2020/3/6.
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private String tag;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
