package com.signin.dao;

import com.signin.model.SignRecord;
import com.signin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("userDao")
@Mapper
public interface UserDao {
    @Select("SELECT id,name,openid,role,invalid FROM `user` WHERE invalid=1 and openid=#{openid} " +
            " and role=#{roleid}")
    List<User> selUserByOpenID(@Param("openid") String openid,@Param("roleid")String roleid);
}
