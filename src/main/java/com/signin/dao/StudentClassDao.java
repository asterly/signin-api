package com.signin.dao;

import com.signin.model.Class;
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
    @Insert("INSERT INTO `student_class` (`student_id`, `class_id`) VALUES (#{studentId}, #{classId})")
    //@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insertByClassId(@Param("studentId") Long studedntId,@Param("classId") Long classId);

    /**
     * 根据班级id查询当前班级的所有学生信息
     * @param classId
     * @return
     */
    @Select("select a.student_id,c.`name` studentName,a.class_id,b.`name` className,teacher_id\n" +
            "from student_class a,class b,user c \n" +
            "where a.class_id=b.id and a.student_id=c.id and a.state=1 and a.class_id=#{classId}\n")
    List<Map> selStudentClass(@Param("classId") int classId);

    /**
     * 根据openid 查询改学生是否已经存在班级关联记录表中
     * @param openid
     * @return
     */
    @Select("select a.student_id,register_time,a.class_id,a.state,b.name,b.openid,b.role\n" +
            " from student_class a ,user b where b.invalid=1 and a.student_id=b.id \n" +
            "and openid=#{openid} and a.class_id=#{classId}")
    List<Map> selValidStudentInfo(@Param("openid") String openid,@Param("classId") Long classId);

    @Select("select b.id,b.name,b.teacher_id,b.create_time\n" +
            " from student_class a ,class b where b.invalid=0 and a.class_id=b.id \n"+
            "and a.student_id=#{studentId}")
    List<Map> listClassesByStudentId(Long studentId);

    @Select("select student_id from student_class where class_id=#{classId}")
    List<Integer> selStudentIdsByClassId(int classId);
}
