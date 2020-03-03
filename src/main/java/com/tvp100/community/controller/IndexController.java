package com.tvp100.community.controller;

import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tvp100 on 2020/3/2.
 */
@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies){
                if (cookie == null)
                    return "index";
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        return "index";
    }
}
