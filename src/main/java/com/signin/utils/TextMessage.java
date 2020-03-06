package com.signin.utils;

import java.util.Date;

public class TextMessage {
    private String MsgType;
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String Content;

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getMoUserName() {
        return ToUserName;
    }

    public void setToUserName(String moUserName) {
        ToUserName = moUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "MsgType='" + MsgType + '\'' +
                ", MoUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", Content='" + Content + '\'' +
                '}';
    }
}
