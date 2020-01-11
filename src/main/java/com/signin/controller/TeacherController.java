package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.model.Teacher;
import com.signin.service.StudentService;
import com.signin.service.TeacherService;
import com.signin.utils.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.mapstruct.BeforeMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:24
 * @Description:
 */
@Api(tags = {"老师的操作"})
@RestController
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public TeacherController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * 查询老师名下的班级
     * @param req
     * @return
     */
    @ApiOperation("查询老师名下的班级")
    @PostMapping("/findClass")
    @ResponseBody
    public String listClasses(@RequestBody Map<String, String> req) {
        return ResultData.success(teacherService.listClasses(req));
    }

    /**
     * 创建班级
     * @param req
     * @return
     */
    @ApiOperation("创建班级")
    @PostMapping("/createClass")
    @ResponseBody
    public String addClass(@RequestBody Map<String, String> req) {
        try {
            return ResultData.success(teacherService.addClass(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 根据班级id删除班级
     * @param req
     * @return
     */
    @ApiOperation("根据班级id删除班级")
    @DeleteMapping("/deleteClass")
    public String deleteClass(@RequestBody Map<String, String> req) {
        try {
            return ResultData.success(teacherService.deleteClass(Long.parseLong(req.get("classId"))));
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
    @ApiOperation("老师开启签到任务，返回6位数的签到码")
    @PostMapping("/openSign")
    public String openSignTask(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.openSign(req));
    }

    /**
     * 选择班级查询该班级的所有签到
     * @param req
     * @return
     */
    @ApiOperation("选择班级查询该班级的所有签到")
    @PostMapping("/allAttendence")
    public String selAttendenceByClass(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.selAttendenceByClass(req));
    }

    /**
     * 教师选择某次签到查看该次签到具体的情况
     * @param req
     * @return
     */
    @ApiOperation("教师选择某次签到查看该次签到具体的情况")
    @PostMapping("/signRecord")
    public String selSignRecordByAttendence(@RequestBody Map<String, String> req){
        return ResultData.success(teacherService.selSignRecordByAttendence(req));
    }
}
