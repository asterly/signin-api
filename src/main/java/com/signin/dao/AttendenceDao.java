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
    @Insert("INSERT INTO `attendence` (name, start_time, end_time, user_id, class_id,sign_code)" +
            "VALUES(#{name}, #{startTime}, #{endTime}, #{userId},#{classId},#{signCode})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Attendence c);

    @Select("select * from attendence where user_id=#{teacherId} and class_id=#{classId} order by id")
    List<Attendence> selAttendenceByClass(@Param("teacherId") int teacherId,@Param("classId") int classId);

    @Select("select class_id from attendence where sign_code=#{signCode}")
    Long findClassIdBySignCode(int signCode);

    @Select("select user_id from attendence where sign_code=#{signCode}")
    Long findTeacherIdBySignCode(int signCode);

    @Select("select id from attendence where sign_code=#{signCode}")
    Long findAttendenceIdBySignCode(int signCode);
}
