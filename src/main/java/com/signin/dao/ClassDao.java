package com.signin.dao;

import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import com.signin.model.Class;

import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/9 12:39
 * @Description:
 */
@Component("classDao")
@Mapper
public interface ClassDao {
    @Insert("INSERT INTO `class`() VALUES()")
    Long insert(Class c);

    @Select("SELECT * FROM `class` WHERE teacher_id=#{id} ORDER BY id DESC")
    List<Class> list(Teacher teacher);
}
