package com.signin.controller;

import com.signin.common.ResultData;
import com.signin.model.Student;
import com.signin.repository.StudentRepository;
import com.signin.service.StudentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    @ResponseBody
    String list() {
        Student student = new Student();
        List<Integer> records =  new ArrayList<>();
        records.add(0);
        records.add(1);
        student.setId(System.currentTimeMillis());
        student.setRecords(records);
        student.setName("hello");
        studentRepository.save(student);
        return ResultData.success("hello");
    }
}
