package com.signin.controller;

import com.signin.common.ResultData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:23
 * @Description:
 */
@Api("学生接口部分")
@RestController
public class StudentController {
    @GetMapping("/students")
    @ResponseBody
    String list() {
        return ResultData.success("hello");
    }
}
