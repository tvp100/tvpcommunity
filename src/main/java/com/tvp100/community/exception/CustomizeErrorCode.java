package com.tvp100.community.exception;

/**
 * Created by tvp100 on 2020/4/1.
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不见了"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登陆，按确定键登陆，请登陆后重试"),
    SYS_ERROR(2004,"服务器异常，稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了,换个评论试试吧~"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"禁止读取别人信息，请重新登陆你的账号"),
    NOTIFICATION_NOT_FOUND(2009,"消息不见了！")
    ;

    private String message;
    private Integer code;
    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code,String message){
        this.message = message;
        this.code = code;
    }




}
