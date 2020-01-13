package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.model.Teacher;
import com.signin.service.StudentClassService;
import com.signin.service.StudentService;
import com.signin.service.TeacherService;
import com.signin.utils.UserInfoUtil;
import io.swagger.annotations.*;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.mapstruct.BeforeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:24
 * @Description:
 */
@Api(tags = {"老师的操作"})
@RestController
public class TeacherController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StudentClassService service;
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
        UserInfoUtil.parseUser(request,req);
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent",value = "学长未解释该参数含义",dataType = "string"),
            @ApiImplicitParam(name = "className",value = "创建班级的名称",dataType = "string")})
    public String addClass(@RequestBody Map<String, String> req) {
        try {
            UserInfoUtil.parseUser(request,req);
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
    @ApiImplicitParam(name = "classId",value = "删除班级的id",dataType = "string")
    public String deleteClass(@RequestBody Map<String, String> req) {
        try {
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.deleteClass(Long.parseLong(req.get("classId"))));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 根据班级号查询当前班级的所有学生信息
     * @param classId 所需要传入的班级ID
     * @return
     */
    @ApiOperation("根据班级号查询当前班级的所有学生信息")
    @GetMapping("/selClassStudent")
    public String selStudentClass(@ApiParam(name = "classId",value = "班级id") @RequestParam("classId") Integer classId) {
        Map req=new HashMap();
        req.put("classId",classId);
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(service.selStudentClass(req));
    }

    /**
     * 教师端创建一个签到任务，返回6位数的签到码
     * @param req 前台页面传入的参数
     *            主要有 teacherID， classID 均为数值类型
     * @return
     */
    @ApiOperation("老师开启签到任务，返回6位数的签到码")
    @PostMapping("/openSign")
    @ApiImplicitParam(name = "classId",value = "发起签到班级的id",dataType = "string")
    public String openSignTask(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(teacherService.openSign(req));
    }

    /**
     * 选择班级查询该班级的所有签到
     * @param req
     * @return
     */
    @ApiOperation("选择班级查询该班级的所有签到")
    @PostMapping("/allAttendence")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "string")
    public String selAttendenceByClass(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(teacherService.selAttendenceByClass(req));
    }

    /**
     * 教师选择某次签到查看该次签到具体的情况
     * @param req
     * @return
     */
    @ApiOperation("教师选择某次签到查看该次签到具体的情况")
    @PostMapping("/signRecord")
    @ApiImplicitParam(name = "attendenceId",value = "查看签到的id",dataType = "string")
    public String selSignRecordByAttendence(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(teacherService.selSignRecordByAttendence(req));
    }
}
