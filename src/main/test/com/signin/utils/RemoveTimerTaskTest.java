package com.signin.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;


public class RemoveTimerTaskTest {
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    @Test
    public void test01() throws InterruptedException {
        int index = 1;
        for (int i = 0; i < 100000; i++) {
            RandomSignCode.codeMap.put("15466_" + i, i);
        }
        while (true) {

            Thread.sleep(100);
            RemoveTimerTask.removeCode("15466_" + index, new Date().getTime() + 3000);
            //System.out.println(RandomSignCode.codeMap.size());
            index++;
            //if(index>100) break;
        }
    }

    @Test
    public void test02() {

        for (int i = 0; i < 10000; i++) {
            String s = generateShortUuid();
            System.out.println(s);
        }
    }


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();


    }
}
