package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.service.StudentClassService;
import com.signin.service.TeacherService;
import com.signin.utils.UserInfoUtil;
import io.swagger.annotations.*;
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
    private HttpServletRequest request;
    private StudentClassService service;
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService,HttpServletRequest request,StudentClassService service) {
        this.teacherService = teacherService;
        this.request = request;
        this.service = service;
    }

    /**
     * 将老师的信息录入数据库
     * @param req
     * @return
     */
    @ApiOperation("老师填写自己的姓名注册")
    @PostMapping("/teacher/register")
    @ApiImplicitParam(name = "name",value = "老师姓名",dataType = "String")
    public String register(@RequestBody Map req){
        try{
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.register(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 查询老师名下的班级
     * @return
     */
    @ApiOperation("查询老师名下的班级")
    @GetMapping("/teacher/classes")
    @ResponseBody
    public String listClasses() {
        try{
            Map req = new HashMap();
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.listClasses(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 创建班级
     * @param req
     * @return
     */
    @ApiOperation("创建班级")
    @PostMapping("/teacher/classes")
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

    @ApiOperation("根据班级id查询该班级的老师姓名")
    @GetMapping("/teacher/name")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "long")
    public String findName(@RequestParam("classId") Long classId){
        try {
            return ResultData.success(teacherService.findName(classId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    @ApiOperation("根据班级id查询该班级的信息")
    @GetMapping("/teacher/classInfo")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "long")
    public String selClassInfo(@RequestParam("classId") Long classId){
        try {
            return ResultData.success(teacherService.selClassInfo(classId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 根据班级id删除班级
     * @param classId
     * @return
     */
    @ApiOperation("根据班级id删除班级")
    @GetMapping("/teacher/class")
    @ApiImplicitParam(name = "classId",value = "删除班级的id",dataType = "long")
    public String deleteClass(@RequestParam("classId") Long classId) {
        try {
            return ResultData.success(teacherService.deleteClass(classId));
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
    @ApiOperation("根据班级id查询当前班级的所有学生信息")
    @GetMapping("/teacher/students")
    public String selStudentClass(@ApiParam(name = "classId",value = "班级id") @RequestParam("classId") Integer classId) {
        try{
            Map req=new HashMap();
            req.put("classId",classId);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(service.selStudentClass(req));
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
    @PostMapping("/teacher/attendences")
    @ApiImplicitParam(name = "classId",value = "发起签到班级的id",dataType = "long")
    public String openSignTask(@RequestBody Map<String, String> req){
        try{
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.openSign(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 选择班级查询该班级的所有签到
     * @param classId
     * @return
     */
    @ApiOperation("选择班级查询该班级的所有签到")
    @GetMapping("/teacher/attendences")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "long")
    public String selAttendenceByClass(@RequestParam("classId") Long classId){
        try{
            Map req = new HashMap();
            req.put("classId", classId);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.selAttendenceByClass(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    @ApiOperation("教师根据签到码查看当前签到具体的情况")
    @GetMapping("/teacher/signRecord")
    @ApiImplicitParam(name = "signCode",value = "输入的签到码")
    public String selSignRecordBySignCode(@RequestParam("signCode") Long signCode){
        try{
            Map req = new HashMap();
            req.put("signCode", signCode);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.selSignRecordBySignCode(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 教师选择某次签到查看该次签到具体的情况
     * @param attendenceId
     * @return
     */
    @ApiOperation("教师选择某次签到查看该次签到具体的情况")
    @GetMapping("/teacher/signRecords")
    @ApiImplicitParam(name = "attendenceId",value = "查看签到的id",dataType = "long")
    public String selSignRecordByAttendence(@RequestParam("attendenceId") Long attendenceId){
        try{
            Map req = new HashMap();
            req.put("attendenceId", attendenceId);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(teacherService.selSignRecordByAttendence(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }
}
