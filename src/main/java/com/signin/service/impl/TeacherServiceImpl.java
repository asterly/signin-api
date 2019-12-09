package com.signin.service.impl;

import com.signin.dao.ClassDao;
import com.signin.dao.TeacherDao;
import com.signin.model.Teacher;
import com.signin.service.TeacherService;
import org.springframework.stereotype.Service;
import com.signin.model.Class;
import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:50
 * @Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;
    private final ClassDao classDao;

    public TeacherServiceImpl(TeacherDao teacherDao, ClassDao classDao) {
        this.teacherDao = teacherDao;
        this.classDao = classDao;
    }

    @Override
    public List<Teacher> list() {
        return teacherDao.list();
    }

    @Override
    public Long addClass(String className, Long teacherId) {
        return null;
    }

    @Override
    public List<Class> listClasses(Teacher teacher) {
        return classDao.list(teacher);
    }

}
