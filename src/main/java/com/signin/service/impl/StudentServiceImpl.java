package com.signin.service.impl;

import com.signin.dao.AttendenceDao;
import com.signin.dao.SignRecordDao;
import com.signin.dao.UserDao;
import com.signin.model.*;
import com.signin.service.StudentService;
import com.signin.utils.RandomSignCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019/12/6 10:50
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService {
    private final MongoTemplate mongoTemplate;

    private final SignRecordDao signRecordDao;

    private final AttendenceDao attendenceDao;

    private final UserDao userDao;

    public StudentServiceImpl(MongoTemplate mongoTemplate,SignRecordDao signRecordDao,AttendenceDao attendenceDao,UserDao userDao) {
        this.mongoTemplate = mongoTemplate;
        this.signRecordDao=signRecordDao;
        this.attendenceDao=attendenceDao;
        this.userDao=userDao;
    }


    @Override
    public String signIN(Map<String, Object> req) {
        String signCode = req.get("signCode").toString();
        String teacherID = attendenceDao.findTeacherIdBySignCode(Integer.parseInt(signCode)).toString();
        String classID = attendenceDao.findClassIdBySignCode(Integer.parseInt(signCode)).toString();
        String userID = req.get("userId").toString();
        //读取内存中的签到目标对象
        Attendence attendence=null;
        try {
            attendence =(Attendence) RandomSignCode.getCode(teacherID + "_" + classID);
        }catch (Exception e){
            return "签到失败，签到结束";
        }

        if(attendence==null){
            return "签到失败，签到结束";
        }

        if(signCode.equalsIgnoreCase(attendence.getSignCode().toString())){
            SignRecord signRecord = new SignRecord();
            signRecord.setAttendenceId(attendence.getId());
            signRecord.setUserId(Long.parseLong(userID));

            //判断是否已经签到
            List signList = signRecordDao.selRecordByAttendenceID(signRecord);
            if(signList.size()>0){
                return "您已经签到过了";
            }
            //入库到签到清单表
            Long insertCode = signRecordDao.insert(signRecord);
            if(insertCode>0){
                return "签到成功！";
            }
            return "签到失败";
        }
        return "签到失败！签到码错误";
    }

    public String studentJoinClass(Map<String, String> req){
        String classId = req.get("classId");

        return "";
    }

    @Override
    public Map<String, String> findAllSignRecord(Map req) {
        Long studentId = (Long) (req.get("userId")) ;
        int classId = Integer.parseInt(req.get("classId").toString());
        //1.根据班级id查询该班级的所有签到id和时间
        List<Map> attendences = attendenceDao.selAttendenIdsceByClass(classId);
        //2.根据签到id查询该学生是否参加了此次签到
        Map<String,String> map = new HashMap<String,String>();
        for(Map attendence:attendences){
            String str = "该次未签到";
            SignRecord signRecord = new SignRecord();
            Long attendenceId = (Long)(attendence.get("id"));
            String time = attendence.get("st").toString();
            signRecord.setAttendenceId(attendenceId);
            signRecord.setUserId(studentId);
            List signList = signRecordDao.selRecordByAttendenceID(signRecord);
            if(signList.size()>0){
                str =  "该次已签到";
            }
            map.put(time, str);
        }
        return map;
    }

    @Override
    public String isSign(Map req) {
        SignRecord signRecord = new SignRecord();
        Long attendenceId = (Long) (req.get("attendenceId"));
        Long studentId = (Long) (req.get("userId"));
        signRecord.setAttendenceId(attendenceId);
        signRecord.setUserId(studentId);
        List signList = signRecordDao.selRecordByAttendenceID(signRecord);
        if(signList.size()>0){
            return "该次已签到";
        }
        return "该次未签到";
    }

    @Override
    public User register(Map req) {
        String name = (String) req.get("name");
        String openid = (String) req.get("openid");
        User user = new User(name,openid,0,100003);
        return userDao.insert(user) > 0 ? user : null;
    }
}
