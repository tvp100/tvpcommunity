package com.tvp100.community.controller;

import com.tvp100.community.dto.PaginationDTO;
import com.tvp100.community.service.QuestionService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by tvp100 on 2020/3/2.
 */
@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search",required = false)String search) {
        PaginationDTO pagination = questionService.list(search,page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }

    @GetMapping("/mySignIn")
    public String signin(){
        return "signIn";
    }

    @GetMapping("/mySignUp")
    public String signup(){
        return "signUp";
    }

}
