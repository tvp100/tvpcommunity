package com.tvp100.community.mode;

import lombok.Data;

/**
 * Created by tvp100 on 2020/3/3.
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
