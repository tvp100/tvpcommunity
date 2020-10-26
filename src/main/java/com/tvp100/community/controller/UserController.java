package com.tvp100.community.controller;

import com.tvp100.community.dto.CommentDTO;
import com.tvp100.community.dto.ResultDTO;
import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.User;
import com.tvp100.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by tvp100 on 2020/4/5.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/userSignUp")
    public ResultDTO signUp(@RequestBody User user,
                         HttpServletResponse response){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success");
        User exitUser = null;
        try {
            exitUser = userService.getUser(user.getAccountId());
        } catch (Exception e) {
            //插入数据
            if (user.getSex() == 0) {
                //0为女生
                user.setAvatarUrl("/image/Girl-avatar.png");
            }else {
                //1为男生
                user.setAvatarUrl("/image/Boy-avatar.png");
            }
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtCreate(System.currentTimeMillis());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.insertUser(user);
            response.addCookie(new Cookie("token", token));
            return resultDTO;
        }
        if (exitUser!=null && user.getAccountId().equals(exitUser.getAccountId())){
            resultDTO.setMessage("账号已存在");
            resultDTO.setCode(300);
            return resultDTO;
        }

        return resultDTO;
    }


    @ResponseBody
    @RequestMapping("/userSignIn")
    public ResultDTO signIn(@RequestBody User user,
                         HttpServletResponse response,
                         Model model){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(300);
        String message = userService.verifyUser(user.getAccountId(),user.getPwd());
        if (message.equals("success")){
            User dbuser = userService.getUser(user.getAccountId());
            String token = UUID.randomUUID().toString();
            dbuser.setToken(token);
            dbuser.setGmtModified(System.currentTimeMillis());
            userService.UpdateUser(dbuser);
            response.addCookie(new Cookie("token",token));
            resultDTO.setCode(200);
            resultDTO.setMessage("登陆成功");
            return resultDTO;
        }
        resultDTO.setMessage(message);
        return resultDTO;
    }
}
