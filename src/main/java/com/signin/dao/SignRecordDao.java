package com.signin.dao;


import com.signin.model.Class;
import com.signin.model.SignRecord;
import com.signin.model.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("signRecordDao")
@Mapper
public interface SignRecordDao {
    @Insert("INSERT INTO `sign_record` ( `userId`, `attendenceId`) VALUES(#{userId}, #{attendenceId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(SignRecord c);

    @Select("SELECT id,attendenceId,userId,signTime FROM `sign_record` WHERE attendenceId=#{attendenceId} AND userId=#{userId} ORDER BY id DESC")
    List<Map> selRecordByAttendenceID(SignRecord signRecord);

    @Select("select * from sign_record where attendenceId=#{attendenceId} order by userId")
    List<SignRecord> selAllRecordByAttendenceId(int attendenceId);

    @Select("select * from sign_record s,attendence a \n"+
            "where s.userId=#{studentId} and s.attendenceId=a.id and a.classId=#{classId} \n")
    List<Map> selRecordByStu(int studentId,int classId);
}
