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
    @Insert("INSERT INTO `attendence` (name, startTime, endTime, userId, classId,signCode)" +
            "VALUES(#{name}, #{startTime}, #{endTime}, #{userId},#{classId},#{signCode})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Attendence c);

    @Select("select * from attendence where userId=#{teacherId} and classId=#{classId} order by id")
    List<Attendence> selAttendenceByClass(@Param("teacherId") int teacherId,@Param("classId") int classId);
}
