package com.signin.service.impl;

import com.signin.dao.AttendenceDao;
import com.signin.dao.SignRecordDao;
import com.signin.dao.UserDao;
import com.signin.model.*;
import com.signin.service.StudentService;
import com.signin.utils.RandomSignCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Map> check = attendenceDao.check(signCode);
        if(check.size()>0){
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
                signRecord.setState(0);
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
        } else{
            return "签到码错误";
        }
    }

    @Override
    public List<Map> findAllSignRecord(Map req) {
        List<Map> list = new ArrayList<Map>();
        int classId = Integer.parseInt(req.get("classId").toString());
        int studentId = Integer.parseInt(req.get("userId").toString());
        list = attendenceDao.findAllSignRecord(classId,studentId);
        //遍历结果，根据签到状态数字将其改为对应的签到状态
        for(Map map:list){
            int state = Integer.parseInt(map.get("state").toString());
            if(state == 0){
                map.put("state", "未签到");
            } else{
                map.put("state", "已签到");
            }
        }
        return list;
    }

    @Override
    public String isSign(Map req) {
        int attendenceId = Integer.parseInt (req.get("attendenceId").toString());
        int studentId = Integer.parseInt (req.get("userId").toString());
        int state = signRecordDao.selSingleSignRecord(attendenceId,studentId);
        if(state == 1){
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
