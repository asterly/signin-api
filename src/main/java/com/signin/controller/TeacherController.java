package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.model.Teacher;
import com.signin.service.StudentService;
import com.signin.service.TeacherService;
import io.swagger.annotations.Api;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public String listClasses(@RequestBody Teacher teacher) {//查询名下所有班级
        return ResultData.success(teacherService.listClasses(teacher));
    }

    @PostMapping("/class")
    @ResponseBody
    public String addClass(@RequestBody Map<String, String> req) {//创建班级
        try {
            return ResultData.success(teacherService.addClass(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    @DeleteMapping("/class")
    public String deleteClass(@RequestBody Map<String, String> req) {
        try {
            return ResultData.success(teacherService.deleteClass(Long.parseLong(req.get("id"))));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 教师端创建一个签到任务，返回6位数的签到码
     * @param req 前台页面传入的参数
     *            主要有 teacherID， classID 均为数值类型
     * @return
     */
    @PostMapping("/openSign")
    public String openSignTask(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.openSign(req));
    }

    /**
     * 教师选择班级查询该班级的所有签到
     * @param req
     * @return
     */
    @PostMapping("/allAttendence")
    public String selAttendenceByClass(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.selAttendenceByClass(req));
    }

    /**
     * 教师选择某次签到查看该次签到具体的情况
     * @param req
     * @return
     */
    @PostMapping("/signRecord")
    public String selSignRecordByAttendence(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.selSignRecordByAttendence(req));
    }
}
