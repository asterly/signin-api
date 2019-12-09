package com.signin.model;

import org.springframework.data.annotation.Id;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:49
 * @Description:
 */
public class Teacher {
    @Id
    private Long id;
    private String name;
    private String dept;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher(Long id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
