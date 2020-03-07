package com.tvp100.community.controller;

import com.tvp100.community.dto.PaginationDTO;
import com.tvp100.community.dto.QuestionDTO;
import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.User;
import com.tvp100.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tvp100 on 2020/3/2.
 */
@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return "index";
        for (Cookie cookie : cookies) {
            if (cookie == null)
                return "index";
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
