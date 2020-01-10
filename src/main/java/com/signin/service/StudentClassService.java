package com.signin.service;

import java.util.List;
import java.util.Map;

public interface StudentClassService {

    List<Map> selStudentClass(Map classid);

    Long insertByClassId(Map req);

    Long insertByClassName(Map req);
}
