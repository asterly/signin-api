package com.signin.common;

/**
 * @Auther: engow
 * @Date: 2019-02-28 20:03
 * @Description:
 */
class ResultStatus {
//    public static final ResultData SUCCESS = "";
//    REPEAT(1101,"数据库内容已存在"),
//    SUCCESS(1200, "成功"),
//    ERROR_REQUEST(1400, "错误的请求"),
//    UNAUTHORIZED(1401, "没有授权"),
//    FORBIDDEN(1403, "没有权限访问"),
//    ERROR_USERNAME(14031, "用户名错误"),
//    ERROR_PASSWORD(14032, "密码错误"),
//    TOKEN_EXPIRED(14033, "TOKEN过期"),
//    NOT_FOND(1404, "页面不存在"),
//    SERVER_ERROR(1500, "服务器错误"),
//    ERROR(9999, "其他异常");
//    NONE(6404, "内容不存在");

    private Integer code;
    private String message;

    private ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    Integer getCode() {
        return code;
    }

    void setCode(Integer code) {
        this.code = code;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    static ResultStatus SUCCESS = new ResultStatus(6200, "成功");
    static ResultStatus SERVER_ERROR = new ResultStatus(6500, "服务器内部错误");
    static ResultStatus ERROR = new ResultStatus(6666, "未知错误");
    static ResultStatus NONE = new ResultStatus(6404, "没有该内容");
}
