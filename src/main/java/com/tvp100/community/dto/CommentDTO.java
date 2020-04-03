package com.tvp100.community.dto;

import lombok.Data;

/**
 * Created by tvp100 on 2020/4/1.
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
