package com.signin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RemoveTimerTask extends TimerTask {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private String codeKey;

    private static Timer timer;


    public RemoveTimerTask() {
    }

    public RemoveTimerTask(String codeKey) {
        this.codeKey = codeKey;
    }
    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeKey() {
        return codeKey;
    }
    static {
        if(timer==null){
            timer=new Timer();
        }
    }

    @Override
    public void run() {

        if(RandomSignCode.codeMap.size()<0){
            return;
        }

        RandomSignCode.codeMap.remove(this.codeKey);
        logger.debug(this.codeKey);
        //System.out.println(this.codeKey);

    }

    public static void removeCode(String codekey,Long excuteTime){
        //执行一次的定时任务
        timer.schedule(new RemoveTimerTask(codekey),new Date(excuteTime));
    }


}
