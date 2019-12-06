package com.signin.dao;

import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:45
 * @Description:
 */
@Component("teacherDao")
@Mapper
public interface TeacherDao {
    @Select("SELECT * FROM teacher ORDER BY name")
    List<Teacher> list();
}
