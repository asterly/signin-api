package com.signin.controller;

import com.alibaba.fastjson.JSONObject;
import com.signin.common.ResultData;
import com.signin.repository.StudentRepository;
import com.signin.service.StudentService;
import com.signin.utils.UserInfoUtil;
import com.signin.utils.WeChatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
@Api
@RestController
public class StudentController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

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


    @ApiModelProperty(value = "/signin")
    @PostMapping("/signin")
    public String signin(@RequestBody Map<String, String> req){
        UserInfoUtil.parseUser(request,req);
        System.out.println(JSONObject.toJSONString(req));
        return JSONObject.toJSONString(req);
        //return ResultData.success(studentService.signIN(req));
    }



}
