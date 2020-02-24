package com.signin.dao;

import com.signin.model.Student;
import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:45
 * @Description:
 */
@Component("studentDao")
@Mapper
public interface StudentDao {

    @Insert("insert into student(name) values(#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Student student);
}
