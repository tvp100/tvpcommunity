package com.tvp100.community.service;

import com.tvp100.community.dto.CommentListDTO;
import com.tvp100.community.enums.CommentTypeEnum;
import com.tvp100.community.enums.NotificationStatusEnum;
import com.tvp100.community.enums.NotificationTypeEnum;
import com.tvp100.community.exception.CustomizeErrorCode;
import com.tvp100.community.exception.CustomizeException;
import com.tvp100.community.mapper.*;
import com.tvp100.community.mode.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tvp100 on 2020/4/1.
 */
@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private CommentExtMapper commentExtMapper;

    @Autowired(required = false)
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,String name) {
        if (comment.getParentId()==null || comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            comment.setCommentCount(0L);
            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1L);
            commentExtMapper.incCommentCount(parentComment);
            //创建通知
            Comment questionUsedComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(questionUsedComment.getParentId());
            createNotify(comment, dbComment.getCommentator(), NotificationTypeEnum.REPLAY_COMMENT,question.getTitle(),name,question.getId());
        }else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0L);
            commentMapper.insert(comment);
            //增加评论数
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建通知
            createNotify(comment,question.getCreator(), NotificationTypeEnum.REPLAY_QUESTION,question.getTitle(),name,question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, NotificationTypeEnum notificationType,String title,String name,Long questionId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());//有用
        notification.setType(notificationType.getType());//回复的是评论或者问题
        notification.setOuterid(questionId);//获得回复的问题的id(questionId)
        notification.setNotifier(comment.getCommentator());//获得评论人的id
        if (comment.getCommentator()==receiver){
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
        }else {
            notification.setStatus(NotificationStatusEnum.UREAD.getStatus());
        }
        notification.setReceiver(receiver);//创建问题的人或者是创建回复的人，我们要通知的人
        notification.setCommentName(name);//有用
        notification.setTitle(title);//有用
        notificationMapper.insert(notification);
    }

    public List<CommentListDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        //倒叙排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size()==0){
            return new ArrayList<>();
        }

        //获取去重评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment 为 commentListDTO
        List<CommentListDTO> commentListDTOS = comments.stream().map(comment -> {
            CommentListDTO commentListDTO = new CommentListDTO();
            BeanUtils.copyProperties(comment,commentListDTO);
            commentListDTO.setUser(userMap.get(comment.getCommentator()));
            return commentListDTO;
        }).collect(Collectors.toList());
        return commentListDTOS;
    }
}
