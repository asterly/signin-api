package com.signin.dao;

import com.signin.model.Attendence;
import com.signin.model.Class;
import com.signin.model.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("attendenceDao")
@Mapper
public interface AttendenceDao {
    @Insert("INSERT INTO `attendence` (name, start_time, end_time, user_id, class_id,sign_code) \n" +
            "VALUES(#{name}, #{startTime}, #{endTime}, #{userId},#{classId},#{signCode}) \n")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Attendence c);

    @Select("select a.id,count(b.id) signedNum from attendence a left JOIN sign_record b on a.id=b.attendence_id \n" +
            "where a.class_id=#{classId} group by a.id \n")
    List<Map> selAttendenceByClass(@Param("classId") int classId);

    @Select("select class_id from attendence where sign_code=#{signCode}")
    Long findClassIdBySignCode(int signCode);

    @Select("select user_id from attendence where sign_code=#{signCode}")
    Long findTeacherIdBySignCode(int signCode);

    @Select("select id from attendence where sign_code=#{signCode}")
    Long findAttendenceIdBySignCode(int signCode);

    //根据签到id查询对应的班级id
    @Select("select class_id from attendence where id=#{attendenceId}")
    int findClassIdByAttendenceId(int attendenceId);

    @Select("select id,start_time st from attendence where class_id=#{classId} order by id")
    List<Map> selAttendenIdsceByClass(int classId);
}
