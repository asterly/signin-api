package com.signin.service.impl;

import com.signin.dao.StudentClassDao;
import com.signin.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StudentCLassServiceImpl implements StudentClassService {

    @Autowired
    private StudentClassDao studentClassDao;
    @Override
    public List<Map> selStudentClass(Map req) {

        String classId = req.get("classId").toString();

        return studentClassDao.selStudentClass(Integer.parseInt(classId));
    }

    public Long insert(Map req){
        return studentClassDao.insert(req);
    }
}
