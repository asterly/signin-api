package com.signin.service.impl;

import com.signin.dao.ClassDao;
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

    @Autowired
    private ClassDao classDao;

    @Override
    public List<Map> selStudentClass(Map req) {

        String classId = req.get("classId").toString();

        return studentClassDao.selStudentClass(Integer.parseInt(classId));
    }

    @Override
    public Long insertByClassId(Map req){
        Long studentId = Long.parseLong((String) req.get("studentId"));
        Long classId = Long.parseLong((String) req.get("classId"));
        return studentClassDao.insertByClassId(studentId,classId);
    }

    @Override
    public Long insertByClassName(Map req) {
        Long studentId = Long.parseLong((String) req.get("studentId"));
        String className = (String)req.get("className");
        Long classId = classDao.findClassIdByClassName(className);
        return studentClassDao.insertByClassId(studentId,classId);
    }
}
