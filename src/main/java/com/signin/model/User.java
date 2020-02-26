package com.signin.model;

/**
 * @Auther: engow
 * @Date: 2019/12/9 16:23
 * @Description:
 */
public class User {
    private Long id;
    private String name;
    private String openid;
    private Integer invalid;
    private Integer roleId;

    public User(String name, String openid, Integer invalid, Integer roleId) {
        this.name = name;
        this.openid = openid;
        this.invalid = invalid;
        this.roleId = roleId;
    }

    public User() {

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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
