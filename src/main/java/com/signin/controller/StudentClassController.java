package com.signin.controller;


import com.signin.common.ResultData;
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
    public String selStudentClass(Map req) {
        return ResultData.success(service.selStudentClass(req));
    }

    /**
     * 根据班级id将学生加入班级
     * @param req
     * @return
     */
    @PostMapping("/joinClassById")
    public String joinByClassId(Map req){
        return ResultData.success(service.insertByClassId(req));
    }

    /**
     * 根据班级名称将学生加入班级
     * @param req
     * @return
     */
    @PostMapping("/joinClassByName")
    public String joinByClassName(Map req){
        return ResultData.success(service.insertByClassName(req));
    }
}
