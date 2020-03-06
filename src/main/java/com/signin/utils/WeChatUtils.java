package com.signin.utils;

import com.alibaba.fastjson.JSONObject;
import com.signin.common.Constants;
import com.signin.model.WeixinOauth2Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Auther: engow
 * @Date: 2019/12/10 16:11
 * @Description:
 */
@Component
public class WeChatUtils {
    private OkHttpClient client = new OkHttpClient();

    // 与接口配置信息中的Token要一致
    private static String token = "weixin";


    public String getAccessToken() {
        try {

            String accessTokenJson = this.run("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APPID + "&secret=" + Constants.APPSECRET);
            JSONObject jsonObject = JSONObject.parseObject(accessTokenJson);
            //返回获取到的token
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //
    public String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return Objects.requireNonNull(response.body()).string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * 获取微信公众号关注的用户信息
     *
     * @return
     */
    public WeixinOauth2Token getOauth2AccessToken(String code) {

        WeixinOauth2Token wat = null;
        //String accessToken = getAccessToken();
        //替换code
        String requestUrl = Constants.WECHART_OPENID_URL.replace("REQUEST_CODE", code);
        String userInfoJson = null;
        try {
            userInfoJson = run(requestUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析返回的数据
        JSONObject jsonObject = JSONObject.parseObject(userInfoJson);

        try {

            // 拼接请求地址
            if (null != jsonObject) {

                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));

            }
        } catch (Exception e) {
            wat = null;
            e.printStackTrace();
        }
        return wat;
    }


    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
