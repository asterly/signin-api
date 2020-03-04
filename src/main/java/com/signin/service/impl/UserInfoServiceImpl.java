package com.signin.service.impl;

import com.signin.dao.UserDao;
import com.signin.model.User;
import com.signin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String openid) {
        return userDao.selUserOpenID(openid);
    }



}
