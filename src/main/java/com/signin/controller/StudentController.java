package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.repository.StudentRepository;
import com.signin.service.StudentService;
import com.signin.utils.WeChatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:23
 * @Description:
 */
@Api
@RestController
public class StudentController {
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


    @ApiModelProperty(value = "/test")
    @PostMapping("/signin")
    public String signin(@RequestBody Map<String, String> req){
        return ResultData.success(studentService.signIN(req));
    }



}
