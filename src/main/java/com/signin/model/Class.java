package com.signin.model;

import java.sql.Timestamp;

/**
 * @Auther: engow
 * @Date: 2019/12/9 12:40
 * @Description:
 */
public class Class {
    private Long id;
    private String name;
    private Long parent;
    private Long teacherId;
    private Integer invalid;
    private Timestamp createTime;

    public Class() {
    }

    public Class(String name, Long parent, Long teacherId) {
        this.name = name;
        this.parent = parent;
        this.teacherId = teacherId;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
