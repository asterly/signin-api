package com.signin.service.impl;

import com.signin.dao.TeacherDao;
import com.signin.model.Teacher;
import com.signin.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:50
 * @Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;

    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public List<Teacher> list() {
        return teacherDao.list();
    }
}
