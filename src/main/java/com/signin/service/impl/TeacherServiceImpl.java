package com.signin.service.impl;

import com.signin.dao.ClassDao;
import com.signin.dao.TeacherDao;
import com.signin.model.Teacher;
import com.signin.service.TeacherService;
import org.springframework.stereotype.Service;
import com.signin.model.Class;
import java.util.List;
import java.util.Map;

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
    public Class addClass(Map<String, String> req) {
        Long parent = req.get("parent") != null ? Long.parseLong(req.get("parent")) : 0;
        Class c = new Class(req.get("name"), parent, Long.parseLong(req.get("teacherId")));
        return classDao.insert(c) > 0 ? c : null;
    }

    @Override
    public List<Class> listClasses(Teacher teacher) {
        return classDao.list(teacher);
    }

    @Override
    public Boolean deleteClass(Long classId) {
        return classDao.delete(classId);
    }

}
