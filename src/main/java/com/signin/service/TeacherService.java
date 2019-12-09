package com.signin.service;

import com.signin.model.Class;
import com.signin.model.Teacher;

import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:49
 * @Description:
 */
public interface TeacherService {
    List<Teacher> list();

    Long addClass(String className, Long teacherId);

    List<Class> listClasses(Teacher teacher);
}
