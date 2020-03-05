package com.signin.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * @Auther: engow
 * @Date: 2019-02-28 20:02
 * @Description:
 */
public class ResultData {
    /**
     *包装类用来传输外部数据
     */

    /*状态码*/
    private Object code;

    /*状态信息*/
    private String message;

    /*内容*/
    private Object data = null;

    public ResultData() {
    }

    private ResultData(Object code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    private ResultData(Object code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> message) {
        this.data = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ResultData addMessage(String message){
        this.message = message;
        return this;
    }

    public static String success(Object data) {
        return JSON.toJSONStringWithDateFormat(new ResultData(
                ResultStatus.SUCCESS.getCode(),
                ResultStatus.SUCCESS.getMessage(),
                data),"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
//        return JSONObject.toJSONString(new ResultData(
//                ResultStatus.SUCCESS.getCode(),
//                ResultStatus.SUCCESS.getMessage(),
//                data));
    }

    public static String error() {
        return JSONObject.toJSONString(new ResultData(
                ResultStatus.ERROR.getCode(),
                ResultStatus.ERROR.getMessage()
        ));
    }

    public static String serverError() {
        return JSONObject.toJSONString(new ResultData(
                ResultStatus.SERVER_ERROR.getCode(),
                ResultStatus.SERVER_ERROR.getMessage()));
    }

    public static String none() {
        return JSONObject.toJSONString(new ResultData(
                ResultStatus.NONE.getCode(),
                ResultStatus.NONE.getMessage()));
    }

}
