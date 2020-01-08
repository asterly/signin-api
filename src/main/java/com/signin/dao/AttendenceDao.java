package com.signin.dao;

import com.signin.model.Attendence;
import com.signin.model.Class;
import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT  FROM `class` WHERE invalid=0 AND teacher_id=#{id} ORDER BY id DESC")
    List<Map> list(Teacher teacher);
}
