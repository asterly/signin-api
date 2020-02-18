package com.signin.service;

import com.signin.model.Attendence;
import com.signin.model.Class;
import com.signin.model.SignRecord;
import com.signin.model.Teacher;

import java.util.List;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:49
 * @Description:
 */
public interface TeacherService {
    List<Teacher> list();

    Class addClass(Map req);

    List<Class> listClasses(Map req);

    Boolean deleteClass(Long classId);

    String openSign(Map req);

    List<Attendence> selAttendenceByClass(Map req);

    List<SignRecord> selSignRecordByAttendence(Map req);
}
