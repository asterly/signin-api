package com.signin.controller;

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
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:23
 * @Description:
 */
@Api(tags = {"学生的操作"})
@RestController
public class StudentController {
    //    @Autowired
//    private HttpServletResponse response;
    private HttpServletRequest request;
    private StudentClassService service;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final WeChatUtils weChatUtils;

    public StudentController(StudentService studentService, StudentRepository studentRepository, WeChatUtils weChatUtils,HttpServletRequest request,StudentClassService service) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.weChatUtils = weChatUtils;
        this.request = request;
        this.service = service;
    }

    /**
     * 将学生的信息录入数据库
     * @param req
     * @return
     */
    @ApiOperation("学生填写自己的姓名注册")
    @PostMapping("/student/register")
    @ApiImplicitParam(name = "name",value = "学生姓名",dataType = "String")
    public String register(@RequestBody Map req){
        try{
            String name = req.get("name").toString();
            UserInfoUtil.parseUser(request,req);
            req.put("name", name);
            return ResultData.success(studentService.register(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

//    @GetMapping("/students")
//    @ResponseBody
//    String list() {
//        return ResultData.success(weChatUtils.getAccessToken());
//        Student student = new Student();
//        List<Integer> records =  new ArrayList<>();
//        records.add(0);
//        records.add(1);
//        student.setId(System.currentTimeMillis());
//        student.setRecords(records);
//        student.setName("hello");
//        studentRepository.save(student);
//        return ResultData.success("hello");
//    }

    /**
     * 学生查询自己加入的所有班级
     * @return
     */
    @ApiOperation("学生查询自己加入的所有班级")
    @GetMapping("/student/classes")
    public String listClasses() {
        try{
            Map req = new HashMap();
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(service.listClasses(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 学生根据班级id加入班级
     * @param req
     * @return
     */
    @ApiOperation("学生根据班级id加入班级")
    @PostMapping("/student/classes")
    @ApiImplicitParam(name = "classId",value = "班级id",dataType = "long")
    public String joinByClassId(@RequestBody Map req){
        try{
            UserInfoUtil.parseUser(request,req);
//            if (req.get("classId") != null){
            Long aLong = service.insertByClassId(req);
            if(aLong == -1){
                return ResultData.success("学生已经存在于班级中请不要重复添加");
            }
            return ResultData.success(aLong);
//            } else {
//                Long aLong = service.insertByClassName(req);
//                if(aLong==-1){
//                    return ResultData.success("学生已经存在与班级中请不要重复添加");
//                }
//                return ResultData.success(aLong);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 学生参加签到
     * @param req
     * @return
     */
    @ApiOperation("学生参加签到")
    @PostMapping("/student/sign")
    @ApiImplicitParam(name = "signCode",value = "学生输入的签到码")
    public String signIn(@RequestBody Map<String, Object> req){
        try{
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(studentService.signIN(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 根据班级id查询学生在该班级所有的签到记录
     * @param classId
     * @return
     */
    @ApiOperation("根据班级id查询该学生在该班级所有的签到记录")
    @GetMapping("/student/signRecords")
    @ApiImplicitParam(name = "classId",value = "查询班级的id",dataType = "long")
    public String findAllSignRecord(@RequestParam("classId")Long classId){
        try{
            Map req = new HashMap();
            req.put("classId", classId);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(studentService.findAllSignRecord(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }

    /**
     * 根据签到id查询学生是否参加该次签到
     * @param attendenceId
     * @return
     */
    @CrossOrigin
    @ApiOperation("根据签到id和学生id查询学生是否参加该次签到")
    @GetMapping("/student/sign")
    @ApiImplicitParam(name = "attendenceId",value = "该次签到的id",dataType = "long")
    public String isSign(@RequestParam("attendenceId")Long attendenceId){
        try{
            Map req = new HashMap();
            req.put("attendenceId", attendenceId);
            UserInfoUtil.parseUser(request,req);
            return ResultData.success(studentService.isSign(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.serverError();
        }
    }
}
