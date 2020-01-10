package com.signin.controller;


import com.signin.service.StudentClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api
@RestController
public class StudentClassController {

    @Autowired
    private StudentClassService service;

    /**
     * 根据班级号查询当前班级的所有学生信息
     * @param req
     * @return
     */
    @PostMapping("/selClassStudent")
    public List<Map> selStudentClass(Map req) {
        return service.selStudentClass(req);
    }

    /**
     * 将学生加入班级
     * @param req
     * @return
     */
    @PostMapping("/joinClass")
    public Long insert(Map req){
        return service.insert(req);
    }
}
