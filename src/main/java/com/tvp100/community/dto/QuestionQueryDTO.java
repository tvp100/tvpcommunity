package com.tvp100.community.dto;

import lombok.Data;

/**
 * Created by tvp100 on 2020/4/6.
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
