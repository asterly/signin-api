package com.signin.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;


public class RemoveTimerTaskTest {

    @Test
    public void test01() throws InterruptedException {
        int index=1;
        for (int i = 0; i < 100000; i++) {
            RandomSignCode.codeMap.put("15466_"+i,i);
        }
        while (true) {

            Thread.sleep(100);
            RemoveTimerTask.removeCode("15466_"+index, new Date().getTime() + 3000);
            //System.out.println(RandomSignCode.codeMap.size());
            index++;
            //if(index>100) break;
        }
    }

}
