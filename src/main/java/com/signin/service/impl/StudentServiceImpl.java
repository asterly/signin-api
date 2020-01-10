package com.signin.service.impl;

import com.signin.dao.SignRecordDao;
import com.signin.model.Attendence;
import com.signin.model.SignRecord;
import com.signin.service.StudentService;
import com.signin.utils.RandomSignCode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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

    public StudentServiceImpl(MongoTemplate mongoTemplate,SignRecordDao signRecordDao) {
        this.mongoTemplate = mongoTemplate;
        this.signRecordDao=signRecordDao;

    }


    @Override
    public String signIN(Map<String, String> req) {
        String teacherID = req.get("teacherID");
        String classID = req.get("classID");
        String signCode = req.get("signCode");
        String userID = req.get("userID");
        //读取内存中的签到签到目标对象
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


}
