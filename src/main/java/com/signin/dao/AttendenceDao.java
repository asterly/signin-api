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


    @Select("select a.start_time time,count(state=1 or null) signedNum,count(state=0 or null) unsignedNum \n"+
            "from attendence a left JOIN sign_record b on a.id=b.attendence_id \n" +
            "where a.class_id=#{classId} group by a.id \n")
    List<Map> selAttendenceByClass(@Param("classId") int classId);

    @Select("select class_id from attendence where sign_code=#{signCode}")
    Long findClassIdBySignCode(int signCode);

    @Select("select user_id from attendence where sign_code=#{signCode}")
    Long findTeacherIdBySignCode(int signCode);

    @Select("select start_time time,state from attendence a left join sign_record b \n" +
            "on a.id=b.attendence_id \n" +
            "where a.class_id=#{classId} and b.user_id=#{studentId} \n")
    List<Map> findAllSignRecord(@Param("classId") int classId,@Param("studentId") int studentId);

    @Select("select id from attendence where sign_code=#{signCode}")
    List<Map> check(String signCode);
}
