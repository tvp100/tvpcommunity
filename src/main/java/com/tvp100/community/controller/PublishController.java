package com.tvp100.community.controller;


import com.tvp100.community.dto.QuestionDTO;
import com.tvp100.community.mode.Question;
import com.tvp100.community.mode.User;
import com.tvp100.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tvp100 on 2020/3/5.
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }



    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            @RequestParam("id") Long id,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //为了偷懒不写进数据库里面再搞个查找方法了，其实这里由前端验证比较好，双向验证是最安全的，我就后端投个懒
        String hasTags = "C++JavaPythonphpAndroidiosJavascripthtmlgolangSpringDjangovue.jsreact.jsbootstrapnode." +
                "jsDatabaseMysqlOraclemongodbsqljsonnosqlelasticsearch运维LinuxnginxdockerapachecentOSubuntu" +
                "Debianfabricssh负载均衡gitgithubmacOSvisual-studio-codeWindowsvimintellij-ideaeclipsewebstor" +
                "msvnpycharmvisual-studiophpstormNotePad++";
        //处理tag
        tag = StringUtils.replace(StringUtils.replace(tag,"，",","),",","|");
        String[] targetTag = StringUtils.split(tag,"|");
        for (String realTag : targetTag) {
            if (hasTags.indexOf(realTag)==-1){
                model.addAttribute("error","标签输入不合法，请点击或输入提示标签");
                return "publish";
            }
        }
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
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
