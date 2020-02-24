package com.signin.service;

import com.signin.model.Class;
import com.signin.model.SignRecord;
import com.signin.model.Student;

import java.util.List;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:49
 * @Description:
 */
public interface StudentService {
    String signIN(Map<String, Object> req);

    List<Map> findAllSignRecord(Map<String, String> req);

    String isSign(Map<String, String> req);

    Student register(Map req);
}
