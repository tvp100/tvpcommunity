package com.tvp100.community.service;

import com.tvp100.community.mapper.UserMapper;
import com.tvp100.community.mode.User;
import com.tvp100.community.mode.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tvp100 on 2020/3/30.
 */
@Service
public class UserService {

    @Autowired(required = false)
    UserMapper userMapper;

    public void creatorUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if (users.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example1 = new UserExample();
            example1.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example1);
        }
    }

    public String verifyUser(String username, String pwd) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(username);
        List<User> users = null;
        try {
            users = userMapper.selectByExample(example);
        } catch (Exception e) {
                return "用户不存在";
        }
        if (!users.get(0).getPwd().equals(pwd)){
            return "密码错误";
        }
        if (users.get(0).getPwd().equals(pwd)){
            return "success";
        }
        return "系统异常";
    }

    public User getUser(String accountid) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(accountid);
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }

    public void UpdateUser(User user) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user, example);
    }

    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
