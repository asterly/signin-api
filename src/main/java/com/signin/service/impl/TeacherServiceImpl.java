package com.signin.service.impl;

import com.signin.dao.*;
import com.signin.model.*;
import com.signin.model.Class;
import com.signin.service.TeacherService;
import com.signin.utils.RandomSignCode;
import com.signin.utils.RemoveTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:50
 * @Description:
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private  ClassDao classDao;

    @Autowired
    private  AttendenceDao attendenceDao;

    @Autowired
    private  SignRecordDao signRecordDao;

    @Autowired
    private  UserDao userDao;

    @Override
    public List<User> list() {//查询所有老师
        return userDao.list();
    }

    @Override
    public Class addClass(Map req) {//新增班级
        Long parent = req.get("parent") != null ? Long.parseLong((String) req.get("parent")) : 0;
        Class c = new Class((String) req.get("className"), parent, Long.parseLong((String) req.get("userId")));
        return classDao.insert(c) > 0 ? c : null;
    }

    @Override
    public List<Class> listClasses(Map req) {//根据老师查询其名下的所有班级
        Long userId = (Long) req.get("userId");
        return classDao.list(userId);
    }

    @Override
    public Boolean deleteClass(Long classId) {//删除班级
        return classDao.delete(classId);
    }

    @Override
    public String openSign(Map req) {
        //1、随机生成6位数字的签到码
        String signCode = RandomSignCode.signCode();
        List<User> userInfo = userDao.selUserByOpenID(req.get("openid").toString(),req.get("roleid").toString());
        if(userInfo.size()<1){
            return "当前用户不具有发起签到的权限";
        }
        Long teacherId = userInfo.get(0).getId();
        String classId = (String) req.get("classId");

        Attendence attendence=new Attendence();
        attendence.setClassId(Long.parseLong(classId));
        //获取当前的时间搓
        long time = new Date().getTime();
        attendence.setStartTime(new Timestamp(time));
        //默认签到时间为1分钟内
        attendence.setEndTime(new Timestamp(time+60000L));
        attendence.setName(teacherId+"_"+classId);
        attendence.setUserId(teacherId);
        //写入签到码
        attendence.setSignCode(Long.parseLong(signCode));
        Long insert = attendenceDao.insert(attendence);

        //2、将签到码存入内存和数据库中
        RandomSignCode.setCode(teacherId+"_"+classId,attendence);

        //3、创建定时器，在指定时间后移除内存中的数据
        RemoveTimerTask.removeCode(teacherId+"_"+classId,time+60000L);
        //3、回写签到码
        return signCode;
    }


    @Override
    public List<Attendence> selAttendenceByClass(Map req) {
        int teacherId = Integer.parseInt((String) req.get("userId"));
        int classId = Integer.parseInt((String) req.get("classId"));
        return attendenceDao.selAttendenceByClass(teacherId,classId);
    }

    @Override
    public List<SignRecord> selSignRecordByAttendence(Map req) {
        int attendenceId = Integer.parseInt((String) req.get("attendenceId"));
        return signRecordDao.selAllRecordByAttendenceId(attendenceId);
    }

    @Override
    public User register(Map req) {
        String name = (String) req.get("name");
        String openid = (String) req.get("openid");
        User user = new User(name,openid,0,100001);
        return userDao.insert(user) > 0 ? user : null;
    }

    @Override
    public List<String> findName(Long classId) {
        return userDao.findName(classId);
    }

    @Override
    public List<Class> selClassInfo(Long classId) {
        return classDao.selClassInfo(classId);
    }

    @Override
    public List<SignRecord> selSignRecordBySignCode(Map req) {
        int signCode = Integer.parseInt((String) req.get("signCode"));
        int attendenceId = Integer.parseInt(attendenceDao.findAttendenceIdBySignCode(signCode).toString());
        return signRecordDao.selAllRecordByAttendenceId(attendenceId);
    }
}
