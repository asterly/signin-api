package com.signin.controller;


import com.signin.common.ResultData;
import com.signin.service.StudentClassService;
import com.signin.utils.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"仅根据班级信息进行操作"})
@RestController
public class StudentClassController {

    @Autowired
    private StudentClassService service;

    @Autowired
    private HttpServletRequest request;
    /**
     * 根据班级号查询当前班级的所有学生信息
     * @param classId 所需要传入的班级ID
     * @return
     */
    @ApiOperation("根据班级号查询当前班级的所有学生信息")
    @GetMapping("/selClassStudent")
    public String selStudentClass(@RequestParam("classId") Integer classId) {
        Map req=new HashMap();
        req.put("classId",classId);
        UserInfoUtil.parseUser(request,req);
        return ResultData.success(service.selStudentClass(req));
    }

    /**
     * 根据班级id将学生加入班级
     * @param req
     * @return
     */
    @ApiOperation("根据班级id将学生加入班级")
    @PostMapping("/joinClassById")
    public String joinByClassId(@RequestBody Map req){
        UserInfoUtil.parseUser(request,req);
        Long aLong = service.insertByClassId(req);
        if(aLong==-1){
            return ResultData.success("学生已经存在与班级中请不要重复添加");
        }
        return ResultData.success(aLong);
    }

    /**
     * 根据班级名称将学生加入班级
     * @param req
     * @return
     */
    @ApiOperation("根据班级名称将学生加入班级")
    @PostMapping("/joinClassByName")
    public String joinByClassName(@RequestBody Map req){
        UserInfoUtil.parseUser(request,req);
        Long aLong = service.insertByClassName(req);
        if(aLong==-1){
            return ResultData.success("学生已经存在与班级中请不要重复添加");
        }
        return ResultData.success(aLong);
    }
}
