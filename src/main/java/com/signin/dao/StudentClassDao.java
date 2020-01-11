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
    //@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insertByClassId(@Param("studentId") Long studedntId,@Param("classId") Long classId);

    /**
     * 根据班级id查询当前班级的所有学生信息
     * @param classId
     * @return
     */
    @Select("select a.studentId,c.`name` studentName,a.classId,b.`name` className,teacherId\n" +
            "from student_class a,class b,user c \n" +
            "where a.classId=b.id and a.studentId=c.id and a.state=1 and a.classId=#{classId}\n")
    List<Map> selStudentClass(@Param("classId") int classId);

    /**
     * 根据openid 查询改学生是否已经存在班级关联记录表中
     * @param openid
     * @return
     */
    @Select("select a.studentId,registerTime,a.classId,a.state,b.name,b.openid,b.role\n" +
            " from student_class a ,user b where b.invalid=1 and a.studentId=b.id \n" +
            "and openid=#{openid} and a.classId=#{classId}")
    List<Map> selValidStudentInfo(@Param("openid") String openid,@Param("classId") Long classId);

}
