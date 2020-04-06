package com.tvp100.community.controller;

import com.tvp100.community.dto.NotificationDTO;
import com.tvp100.community.exception.CustomizeErrorCode;
import com.tvp100.community.exception.CustomizeException;
import com.tvp100.community.mapper.NotificationMapper;
import com.tvp100.community.mode.User;
import com.tvp100.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tvp100 on 2020/4/4.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String read(@PathVariable(name = "id")Long id,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        return "redirect:/question/"+notificationDTO.getOuterid();
    }
}
