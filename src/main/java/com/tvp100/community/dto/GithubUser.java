package com.tvp100.community.dto;

import lombok.Data;

/**
 * Created by tvp100 on 2020/3/2.
 */
@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;
}
