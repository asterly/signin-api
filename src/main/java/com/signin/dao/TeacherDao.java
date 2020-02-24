package com.signin.dao;

import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
    List<Teacher> list();//获取所有老师

    @Insert("insert into teacher(name) values(#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Teacher teacher);

    @Select("select teacher.name from teacher,class where class.id=#{classId} and teacher_id=teacher.id order by teacher.id")
    List<String> findName(Long classId);
}
