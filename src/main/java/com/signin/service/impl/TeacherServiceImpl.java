package com.signin.service.impl;

import com.signin.dao.AttendenceDao;
import com.signin.dao.ClassDao;
import com.signin.dao.SignRecordDao;
import com.signin.dao.TeacherDao;
import com.signin.model.Attendence;
import com.signin.model.SignRecord;
import com.signin.model.Teacher;
import com.signin.service.TeacherService;
import com.signin.utils.RandomSignCode;
import com.signin.utils.RemoveTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.signin.model.Class;

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

    private final TeacherDao teacherDao;
    private final ClassDao classDao;
    private final AttendenceDao attendenceDao;

    private final SignRecordDao signRecordDao;

    public TeacherServiceImpl(TeacherDao teacherDao, ClassDao classDao,
                              AttendenceDao attendenceDao,SignRecordDao signRecordDao) {
        this.teacherDao = teacherDao;
        this.classDao = classDao;
        this.attendenceDao=attendenceDao;
        this.signRecordDao=signRecordDao;

    }

    @Override
    public List<Teacher> list() {//查询所有老师
        return teacherDao.list();
    }

    @Override
    public Class addClass(Map<String, String> req) {//新增班级
        Long parent = req.get("parent") != null ? Long.parseLong(req.get("parent")) : 0;
        Class c = new Class(req.get("name"), parent, Long.parseLong(req.get("teacherId")));
        return classDao.insert(c) > 0 ? c : null;
    }

    @Override
    public List<Class> listClasses(Teacher teacher) {//根据老师查询其名下的所有班级
        return classDao.list(teacher);
    }

    @Override
    public Boolean deleteClass(Long classId) {//删除班级
        return classDao.delete(classId);
    }

    @Override
    public String openSign(Map<String, String> req) {
        //1、随机生成6位数字的签到码
        String signCode = RandomSignCode.signCode();
        String teacherId = req.get("teacherId");
        String classId = req.get("classId");

        Attendence attendence=new Attendence();
        attendence.setClassId(Long.parseLong(classId));
        //获取当前的时间搓
        long time = new Date().getTime();
        attendence.setStartTime(new Timestamp(time));
        //默认签到时间为1分钟内
        attendence.setEndTime(new Timestamp(time+60000L));
        attendence.setName(teacherId+"_"+classId);
        attendence.setUserId(Long.parseLong(teacherId));
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
    public List<Attendence> selAttendenceByClass(Map<String, String> req) {
        int teacherId = Integer.parseInt(req.get("teacherId"));
        int classId = Integer.parseInt(req.get("classId"));
        return attendenceDao.selAttendenceByClass(teacherId,classId);
    }

    @Override
    public List<SignRecord> selSignRecordByAttendence(Map<String, String> req) {
        int attendenceId = Integer.parseInt(req.get("attendenceId"));
        return signRecordDao.selAllRecordByAttendenceId(attendenceId);
    }
}
