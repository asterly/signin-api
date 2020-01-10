package com.signin.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("studentClassDao")
@Mapper
public interface StudentClassDao {
    @Insert("INSERT INTO `student_class` (`student_id`, `class_id`) VALUES (#{userid}, #{classid})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Map c);//学生导入班级

    /**
     * 根据班级号查询当前班级的所有学生信息
     * @param classid
     * @return
     */
    @Select("select a.student_id,c.`name` student_name,a.class_id,b.`name` class_name,teacher_id\n" +
            "from student_class a,class b,student c \n" +
            "where a.class_id=b.id and a.student_id=c.id and a.state=1 and a.class_id=#{classid}\n")
    List<Map> selStudentClass(@Param("classid") int classid);
}
