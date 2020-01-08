package com.signin.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomSignCode {

    public static Map codeMap=new HashMap();

    public static Object getCode(String codeKey) {
        return RandomSignCode.codeMap.get(codeKey);
    }

    public static void setCode(String codeKey,String code) {
        RandomSignCode.codeMap.put(codeKey,code);
    }
    public static void setCode(String codeKey,Object code) {
        RandomSignCode.codeMap.put(codeKey,code);
    }

    public static String signCode(){
        char c[]={48,49,50,51,52,53,54,55,56,57};
        Random random=new Random();
        StringBuffer returnCode=new StringBuffer();
        for (int i = 0; i < 6; i++) {
            returnCode.append(c[random.nextInt(10)]);
        }
        return returnCode.toString();
    }

}
