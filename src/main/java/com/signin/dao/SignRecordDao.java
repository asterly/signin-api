package com.signin.dao;


import com.signin.model.Class;
import com.signin.model.SignRecord;
import com.signin.model.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("signRecordDao")
@Mapper
public interface SignRecordDao {
    @Insert("INSERT INTO sign_record (user_id, attendence_id,state) VALUES(#{userId}, #{attendenceId},#{state})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(SignRecord c);

    @Select("SELECT id,attendence_id,user_id,sign_time FROM `sign_record` WHERE attendence_id=#{attendenceId} AND user_id=#{userId} ORDER BY id DESC")
    List<Map> selRecordByAttendenceID(SignRecord signRecord);

    @Select("select * from sign_record where attendence_id=#{attendenceId} order by user_id")
    List<SignRecord> selAllRecordByAttendenceId(int attendenceId);

    @Select("select * from sign_record s,attendence a \n"+
            "where s.user_id=#{studentId} and s.attendence_id=a.id and a.class_id=#{classId} \n")
    List<Map> selRecordByStu(@Param("studentId") int studentId,@Param("classId") int classId);

    @Select("select * from sign_record where attendence_id=#{attendenceId} and user_id=#{studentId}")
    List<SignRecord> isSign(@Param("attendenceId") int attendenceId,@Param("studentId") int studentId);

    @Insert("Insert into sign_record(user_id,attendence_id,state) \n"+
            "select student_id as user_id,#{attendenceId} as attendence_id,0 as state \n"+
            "from student_class where class_id=#{classId} \n")
    Long addAllStudents(@Param("classId") int classId,@Param("attendenceId") int attendenceId);

    @Select("select name,state from user,sign_record where user_id=user.id and attendence_id=#{attendenceId}")
    List<Map> selSignRecordByAttendenceID(int attendenceId);

    @Select("select state from sign_record where attendence_id=#{attendenceId} and user_id=#{studentId}")
    int selSingleSignRecord(@Param("attendenceId") int attendenceId,@Param("studentId") int studentId);
}
