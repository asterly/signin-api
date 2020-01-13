package com.signin.controller;

import com.alibaba.fastjson.JSONObject;
import com.signin.common.ResultData;
import com.signin.repository.StudentRepository;
import com.signin.service.StudentClassService;
import com.signin.service.StudentService;
import com.signin.utils.UserInfoUtil;
import com.signin.utils.WeChatUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:23
 * @Description:
 */
@Api(tags = {"学生的操作"})
@RestController
public class StudentController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StudentClassService service;
//    @Autowired
//    private HttpServletResponse response;

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final WeChatUtils weChatUtils;

    public StudentController(StudentService studentService, StudentRepository studentRepository, WeChatUtils weChatUtils) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.weChatUtils = weChatUtils;
    }

    @GetMapping("/students")
    @ResponseBody
    String list() {
        return ResultData.success(weChatUtils.getAccessToken());
//        Student student = new Student();
//        List<Integer> records =  new ArrayList<>();
//        records.add(0);
//        records.add(1);
//        student.setId(System.currentTimeMillis());
//        student.setRecords(records);
//        student.setName("hello");
//        studentRepository.save(student);
//        return ResultData.success("hello");

    }

    /**
     * 学生根据班级id加入班级
     * @param req
     * @return
     */
    @ApiOperation("学生根据班级id加入班级")
    @PostMapping("/joinClassById")
    @ApiImplicitParam(name = "classId",value = "班级id",dataType = "string")
    public String joinByClassId(@RequestBody Map req){
        UserInfoUtil.parseUser(request,req);
        Long aLong = service.insertByClassId(req);
        if(aLong==-1){
            return ResultData.success("学生已经存在于班级中请不要重复添加");
        }
        return ResultData.success(aLong);
    }

    /**
     * 学生根据班级名称加入班级
     * @param req
     * @return
     */
    @ApiOperation("学生根据班级名称加入班级")
    @PostMapping("/joinClassByName")
    @ApiImplicitParam(name = "className",value = "班级名称",dataType = "string")
    public String joinByClassName(@RequestBody Map req){
        UserInfoUtil.parseUser(request,req);
        Long aLong = service.insertByClassName(req);
        if(aLong==-1){
            return ResultData.success("学生已经存在与班级中请不要重复添加");
        }
        return ResultData.success(aLong);
    }

    /**
     * 学生参加签到
     * @param req
     * @return
     */
    @ApiOperation("学生参加签到")
    @PostMapping("/signIn")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId",value = "发起签到的老师id"),
            @ApiImplicitParam(name = "classId",value = "签到班级的id"),
            @ApiImplicitParam(name = "signCode",value = "学生输入的签到码")})
    public String signIn(@RequestBody Map<String, Object> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(studentService.signIN(req));
    }

    /**
     * 根据班级id和学生id查询学生在该班级所有的签到记录
     * @param req
     * @return
     */
    @ApiOperation("根据班级id和学生id查询该学生在该班级所有的签到记录")
    @PostMapping("/findAllSignRecord")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "string")
    public String findAllSignRecord(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(studentService.findAllSignRecord(req));
    }

    /**
     * 根据签到id和学生id查询学生是否参加该次签到
     * @param req
     * @return
     */
    @ApiOperation("根据签到id和学生id查询学生是否参加该次签到")
    @PostMapping("/isSign")
    @ApiImplicitParam(name = "attendenceId",value = "该次签到的id",dataType = "string")
    public String isSign(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(studentService.isSign(req));
    }
}
