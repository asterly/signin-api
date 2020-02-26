package com.signin.dao;

import com.signin.model.*;
import com.signin.model.Class;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("userDao")
@Mapper
public interface UserDao {

    @Select("SELECT id,name,openid,role,invalid FROM `user` WHERE invalid=1 and openid=#{openid} " +
            " and role=#{roleid}")
    List<User> selUserByOpenID(@Param("openid") String openid,@Param("roleid")String roleid);

    @Insert("INSERT INTO `user`(name, openid, role, invalid) VALUES(#{name}, #{openid}, #{roleId}, #{invalid})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long insert(User user);

    @Select("SELECT * FROM user where role=100001 and invalid=0 ORDER BY name")
    List<User> list();//获取所有老师

    @Select("select user.name from user,class where class.id=#{classId} and teacher_id=user.id order by user.id")
    List<String> findName(Long classId);
}
