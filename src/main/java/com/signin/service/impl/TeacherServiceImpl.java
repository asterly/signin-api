package com.signin.service.impl;

import com.signin.dao.*;
import com.signin.model.*;
import com.signin.model.Class;
import com.signin.service.TeacherService;
import com.signin.utils.Pinyin4j;
import com.signin.utils.RandomSignCode;
import com.signin.utils.RemoveTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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

    @Autowired
    private StudentClassDao studentClassDao;

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
        List<User> userInfo = userDao.selUserByOpenID(req.get("openid").toString(),req.get("roleId").toString());
        if(userInfo.size()<1){
            return "当前用户不具有发起签到的权限";
        }
        Long teacherId = userInfo.get(0).getId();
        String classId = req.get("classId").toString();
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
    public List<Map> selAttendenceByClass(Map req) {
        int classId = Integer.parseInt(req.get("classId").toString());
        return attendenceDao.selAttendenceByClass(classId);
    }

    @Override
    public Map<String,String> selSignRecordByAttendence(Map req) {
        int attendenceId = Integer.parseInt(req.get("attendenceId").toString());
        //1.根据签到id查询对应的班级id
        int classId = attendenceDao.findClassIdByAttendenceId(attendenceId);
        //2.根据班级id查询该班级的所有学生id并且存入集合中
        List<Integer> studentIds = new ArrayList<Integer>();
        studentIds = studentClassDao.selStudentIdsByClassId(classId);
        //3.根据每个学生的id迭代查询其名字跟是否参加签到，并将结果存入集合中
        Map<String,String> map = new HashMap<String,String>();
        for(Integer studentId:studentIds){
            String name = userDao.findNameById(studentId);
            String str = "未签到";
            List<SignRecord> isSign = signRecordDao.isSign(attendenceId,studentId);
            if(isSign.size()>0){
                str = "已签到";
            }
            map.put(name, str);
        }
        //4.将集合返回
        return map;
    }

    @Override
    public User register(Map req) {
        String name = req.get("name").toString();
        String openid = req.get("openid").toString();
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
    public Map<String,String> selSignRecordBySignCode(Map req) {
        int signCode = Integer.parseInt(req.get("signCode").toString());
        int attendenceId = Integer.parseInt(attendenceDao.findAttendenceIdBySignCode(signCode).toString());
        //1.根据签到id查询对应的班级id
        int classId = attendenceDao.findClassIdByAttendenceId(attendenceId);
        //2.根据班级id查询该班级的所有学生id并且存入集合中
        List<Integer> studentIds = new ArrayList<Integer>();
        studentIds = studentClassDao.selStudentIdsByClassId(classId);
        //3.根据每个学生的id迭代查询其名字跟是否参加签到，并将结果存入集合中
        Map<String,String> map = new HashMap<String,String>();
        for(Integer studentId:studentIds){
            String name = userDao.findNameById(studentId);
            String str = "未签到";
            List<SignRecord> isSign = signRecordDao.isSign(attendenceId,studentId);
            if(isSign.size()>0){
                str = "已签到";
            }
            map.put(name, str);
        }
        //4.将集合返回
        return map;
    }

    @Override
    public String selStudent(Map req) {
        int classId = Integer.parseInt(req.get("classId").toString());
        //1.根据班级id查询该班级的所有学生id并且存入集合中
        List<Integer> studentIds = new ArrayList<Integer>();
        studentIds = studentClassDao.selStudentIdsByClassId(classId);
        //2.计算学生的人数并且生成学生数以内的随机数
        int size = studentIds.size();
        Random random = new Random();
        int num = random.nextInt(size);
        //3.根据随机数得到被挑选出来的学生id
        int studentId = studentIds.get(num);
        //4.根据学生id返回学生姓名
        String name = userDao.findNameById(studentId);
        String pinYin = Pinyin4j.getAllPinyin(name);
        return name.concat(pinYin);
    }
}
