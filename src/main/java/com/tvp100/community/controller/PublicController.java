package com.tvp100.community.controller;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tvp100.community.mapper.QuestionMapper;
import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.Question;
import com.tvp100.community.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tvp100 on 2020/3/5.
 */
@Controller
public class PublicController {

    @Autowired(required = false)
     private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return "publish";
        User user = null;
        for (Cookie cookie : cookies){
            if (cookie == null)
                return "index";
            if ("token".equals(cookie.getName())){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        User user = null;
        for (Cookie cookie : cookies){
            if (cookie == null)
                return "index";
            if ("token".equals(cookie.getName())){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
