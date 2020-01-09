package com.signin.dao;

import com.signin.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:45
 * @Description:
 */
@Component("studentDao")
@Mapper
public interface StudentDao {
    @Insert("INSERT INTO `sign_record` ( `user_id`, `attendence_id`) VALUES(#{userId}, #{attendenceId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(Student c);
}
