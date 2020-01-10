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

    Class addClass(Map<String, String> req);

    List<Class> listClasses(Teacher teacher);

    Boolean deleteClass(Long classId);

    String openSign(Map<String, String> req);

    List<Attendence> selAttendenceByClass(Map<String,String> req);

    List<SignRecord> selSignRecordByAttendence(Map<String,String> req);
}
