package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.model.Teacher;
import com.signin.service.StudentService;
import com.signin.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:24
 * @Description:
 */
@Api
@RestController
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public TeacherController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/teachers")
    @ResponseBody
    public String list() {
        return null;
    }

    @GetMapping("/class")
    @ResponseBody
    public String listClasses(@RequestBody Teacher teacher) {
        return ResultData.success(teacherService.listClasses(teacher));
    }

    @PostMapping("/classes")
    @ResponseBody
    public String addClass(String name, Long teacherId) {
        try {
            return ResultData.success(teacherService.addClass(name, teacherId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }
}
