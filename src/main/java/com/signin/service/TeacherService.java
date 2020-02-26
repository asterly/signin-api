package com.signin.service;

import com.signin.model.*;
import com.signin.model.Class;

import java.util.List;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:49
 * @Description:
 */
public interface TeacherService {
    List<User> list();

    Class addClass(Map req);

    List<Class> listClasses(Map req);

    Boolean deleteClass(Long classId);

    String openSign(Map req);

    List<Attendence> selAttendenceByClass(Map req);

    List<SignRecord> selSignRecordByAttendence(Map req);

    User register(Map req);

    List<String> findName(Long classId);

    List<Class> selClassInfo(Long classId);

    List<SignRecord> selSignRecordBySignCode(Map req);
}
