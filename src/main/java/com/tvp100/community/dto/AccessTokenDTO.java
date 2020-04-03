package com.tvp100.community.dto;

import lombok.Data;

/**
 * Created by tvp100 on 2020/3/2.
 */

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
