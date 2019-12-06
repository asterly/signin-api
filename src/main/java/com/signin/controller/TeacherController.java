package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:24
 * @Description:
 */
@Api("教师接口部分")
@RestController
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    @ResponseBody
    public String list() {
        return ResultData.success(teacherService.list());
    }
}
