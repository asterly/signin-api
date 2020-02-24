package com.signin.service.impl;

import com.signin.dao.AttendenceDao;
import com.signin.dao.SignRecordDao;
import com.signin.dao.StudentDao;
import com.signin.model.Attendence;
import com.signin.model.SignRecord;
import com.signin.model.Student;
import com.signin.model.Teacher;
import com.signin.service.StudentService;
import com.signin.utils.RandomSignCode;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final AttendenceDao attendenceDao;

    private final StudentDao studentDao;

    public StudentServiceImpl(MongoTemplate mongoTemplate,SignRecordDao signRecordDao,AttendenceDao attendenceDao,StudentDao studentDao) {
        this.mongoTemplate = mongoTemplate;
        this.signRecordDao=signRecordDao;
        this.attendenceDao=attendenceDao;
        this.studentDao=studentDao;
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
    public List<Map> findAllSignRecord(Map<String, String> req) {
        int studentId = Integer.parseInt(req.get("userId"));
        int classId = Integer.parseInt(req.get("classId"));
        return signRecordDao.selRecordByStu(studentId,classId);
    }

    @Override
    public String isSign(Map<String, String> req) {
        SignRecord signRecord = new SignRecord();
        Long attendenceId = Long.parseLong(req.get("attendenceId"));
        Long studentId = Long.parseLong(req.get("userId"));
        signRecord.setAttendenceId(attendenceId);
        signRecord.setUserId(studentId);
        List signList = signRecordDao.selRecordByAttendenceID(signRecord);
        if(signList.size()>0){
            return "该次已签到";
        }
        return "该次未签到";
    }

    @Override
    public Student register(Map req) {
        String name = (String) req.get("name");
        Student student = new Student(name);
        return studentDao.insert(student) > 0 ? student : null;
    }
}
