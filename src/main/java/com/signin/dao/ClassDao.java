package com.signin.dao;

import com.signin.model.Teacher;
import org.apache.ibatis.annotations.*;
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
    @Insert("INSERT INTO `class`(name, parent, teacher_id, create_time) VALUES(#{name}, #{parent}, #{teacherId}, #{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Class c);

    @Select("SELECT * FROM `class` WHERE invalid=0 AND teacher_id=#{id} ORDER BY id DESC")
    List<Class> list(Teacher teacher);

    @Update("UPDATE `class` SET invalid=1 WHERE id=#{classId}")
    Boolean delete(Long classId);
}
