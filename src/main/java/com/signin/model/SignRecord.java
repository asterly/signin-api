package com.signin.model;

import java.sql.Timestamp;

public class SignRecord {
    private Long id;
    private Long userId;
    private Long classId;
    private Long teacherId;
    private String openid;
    private Long attendenceId;
    private Timestamp signTime;

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
