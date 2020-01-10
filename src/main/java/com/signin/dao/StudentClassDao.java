package com.signin.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("studentClassDao")
@Mapper
public interface StudentClassDao {
    @Insert("INSERT INTO `student_class` (`studentId`, `classId`) VALUES (#{userid}, #{classid})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Map c);//学生导入班级

    /**
     * 根据班级号查询当前班级的所有学生信息
     * @param classid
     * @return
     */
    @Select("select a.studentId,c.`name` studentName,a.classId,b.`name` className,teacherId\n" +
            "from student_class a,class b,student c \n" +
            "where a.classId=b.id and a.studentId=c.id and a.state=1 and a.classId=#{classid}\n")
    List<Map> selStudentClass(@Param("classid") int classid);
}
