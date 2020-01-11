package com.signin.service.impl;

import com.signin.dao.ClassDao;
import com.signin.dao.StudentClassDao;
import com.signin.dao.StudentDao;
import com.signin.dao.UserDao;
import com.signin.model.User;
import com.signin.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StudentCLassServiceImpl implements StudentClassService {

    @Autowired
    private StudentClassDao studentClassDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClassDao classDao;

    @Override
    public List<Map> selStudentClass(Map req) {

        String classId = req.get("classId").toString();

        return studentClassDao.selStudentClass(Integer.parseInt(classId));
    }

    @Override
    public Long insertByClassId(Map req){

        Long classId = Long.parseLong(req.get("classId").toString());
        //校验学生ID是否存在
        List<Map> userInfo = studentClassDao.selValidStudentInfo(req.get("openid").toString(),classId);
        if(userInfo.size()<1){
            //当没有查询到关联记录时执行操作
            String user_id = req.get("user_id").toString();
            Long studentId=Long.parseLong(user_id);
            return studentClassDao.insertByClassId(studentId,classId);
        }
        return -1L;
    }

    @Override
    public Long insertByClassName(Map req) {
        String className = (String)req.get("className");
        Long classId = classDao.findClassIdByClassName(className);
        //校验学生ID是否存在
        List<Map> userInfo = studentClassDao.selValidStudentInfo(req.get("openid").toString(),classId);
        if(userInfo.size()<1){
            //当没有查询到关联记录时执行操作
            String user_id = req.get("user_id").toString();
            Long studentId=Long.parseLong(user_id);
            return studentClassDao.insertByClassId(studentId,classId);
        }
        return -1L;
    }
}
