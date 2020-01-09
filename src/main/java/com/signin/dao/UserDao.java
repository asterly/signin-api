package com.signin.dao;

import com.signin.model.SignRecord;
import com.signin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("userDao")
@Mapper
public interface UserDao {
    @Select("SELECT id,name,openid,role,invalid FROM `user` WHERE openid=#{openid}")
    List<User> selUserByOpenID(String openid);
}
