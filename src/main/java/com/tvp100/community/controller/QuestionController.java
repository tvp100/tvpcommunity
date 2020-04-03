package com.tvp100.community.controller;

import com.tvp100.community.dto.CommentListDTO;
import com.tvp100.community.dto.QuestionDTO;
import com.tvp100.community.enums.CommentTypeEnum;
import com.tvp100.community.service.CommentService;
import com.tvp100.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by tvp100 on 2020/3/30.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model){
        //查找评论
        List<CommentListDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //页面主要信息
        QuestionDTO questionDTO = questionService.getById(id);
        //查找相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //累加浏览数
        questionService.addView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
