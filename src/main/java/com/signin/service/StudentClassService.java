package com.signin.service;

import com.signin.model.Class;

import java.util.List;
import java.util.Map;

public interface StudentClassService {

    List<Map> selStudentClass(Map classid);

    Long insertByClassId(Map req);

    Long insertByClassName(Map req);

    List<Map> listClasses(Map req);
}
