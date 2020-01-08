package com.signin.model;

import org.springframework.context.annotation.Description;

import java.sql.Timestamp;

/*
@Auther engow
@Date 2019129 1621
@Description
*
 */
public class Attendence {
    private Long id;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private Long userId;
    private Long classId;
    private Long signCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public void setSignCode(Long signCode) {
        this.signCode = signCode;
    }

    public Long getSignCode() {
        return signCode;
    }
}
