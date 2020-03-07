package com.tvp100.community.mode;

import lombok.Data;

/**
 * Created by tvp100 on 2020/3/5.
 */
@Data
public class Question {
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

}
