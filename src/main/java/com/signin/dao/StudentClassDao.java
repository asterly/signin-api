package com.signin.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("studentClassDao")
@Mapper
public interface StudentClassDao {
    /**
     * 根据班级id将学生加入班级
     * @param studedntId
     * @param classId
     * @return
     */
    @Insert("INSERT INTO `student_class` (`studentId`, `classId`) VALUES (#{studentId}, #{classId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insertByClassId(Long studedntId,Long classId);

    /**
     * 根据班级id查询当前班级的所有学生信息
     * @param classId
     * @return
     */
    @Select("select a.studentId,c.`name` studentName,a.classId,b.`name` className,teacherId\n" +
            "from student_class a,class b,student c \n" +
            "where a.classId=b.id and a.studentId=c.id and a.state=1 and a.classId=#{classId}\n")
    List<Map> selStudentClass(@Param("classId") int classId);

}
