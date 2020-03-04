package com.signin.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class SignRecord {
    private Long id;
    private Long userId;
    private Long classId;
    private Long teacherId;
    private String openid;
    private Long attendenceId;
    @JSONField(format="yyyy-MM-dd hh:mm:ss")
    private Timestamp signTime;
    private Integer state;

    public SignRecord() {
    }

    public SignRecord(Long userId, Long attendenceId, Integer state) {
        this.userId = userId;
        this.attendenceId = attendenceId;
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setAttendenceId(Long attendenceId) {
        this.attendenceId = attendenceId;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClassId() {
        return classId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public String getOpenid() {
        return openid;
    }

    public Long getAttendenceId() {
        return attendenceId;
    }

    public Timestamp getSignTime() {
        return signTime;
    }
}
