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
    @Insert("INSERT INTO `sign_record` ( `user_id`, `attendence_id`) VALUES(#{userId}, #{attendenceId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(SignRecord c);

    @Select("SELECT id,attendence_id,user_id,sign_time FROM `sign_record` WHERE attendence_id=#{attendenceId} AND user_id=#{userId} ORDER BY id DESC")
    List<Map> selRecord(SignRecord signRecord);
}
