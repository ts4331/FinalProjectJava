package com.koudinglang.main.model;

import com.koudinglang.main.util.SocketUtil;

import java.io.Serializable;

/**
 * @Author: duan
 * @Date: Create in 14:08 2021/3/25
 */
public class MyMessage implements Serializable {
    //消息的主体内容
    private Object content;
    //消息类型
    private Type type;
    //消息发起者用户名
    private String from;
    //消息接收者用户名
    private String to;
    //消息发起者阵营
    private int fromPlayer;
    //消息接收者阵营
    private int toPlayer;

    public enum Type {
        LOGIN,//登录
        REG,//注册
        FORGET,//忘记密码
        LIST,//获取当前服务登录的所有人
        FIGHT,//发起对战
        FIGHT_SUCCESS,//发起对战成功
        MOVE,//移动棋子
        EAT,//吃子
        PEACE,//求和
        PEACE_SUCCESS,//求和成功
        PEACE_FAILURE,//求和失败
        SUCCESS,//发送成功
        FAILURE,//发送失败
        PEFEAT,//认输
        HuiQi//悔棋
    }

    public MyMessage() {

    }

    public MyMessage(Object content, Type type, String from, String to) {
        this.content = content;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public Object getContent() {
        return content;
    }

    public MyMessage setContent(Object content) {
        this.content = content;
        return this;
    }

    public Type getType() {
        return type;
    }

    public MyMessage setType(Type type) {
        this.type = type;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public MyMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MyMessage setTo(String to) {
        this.to = to;
        return this;
    }

    public int getFromPlayer() {
        return fromPlayer;
    }

    public MyMessage setFromPlayer(int fromPlayer) {
        this.fromPlayer = fromPlayer;
        return this;
    }

    public int getToPlayer() {
        return toPlayer;
    }

    public MyMessage setToPlayer(int toPlayer) {
        this.toPlayer = toPlayer;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", from='").append(from).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", fromPlayer=").append(fromPlayer);
        sb.append(", toPlayer=").append(toPlayer);
        sb.append('}');
        return sb.toString();
    }

}
