package com.signin.utils;

import java.util.Date;
import java.util.Timer;

public class RemoveTimer {
    private static Timer timer;

    public static void removeCode(String codekey,Long excuteTime){
        if(timer==null){
            timer=new Timer();
        }
        //船舰执行一次的定时任务
        timer.schedule(new RemoveTimerTask(codekey),new Date(excuteTime));
    }

}
