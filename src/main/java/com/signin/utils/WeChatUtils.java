package com.signin.utils;

import com.alibaba.fastjson.JSONObject;
import com.signin.common.Constants;
import com.signin.model.WeixinOauth2Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @Auther: engow
 * @Date: 2019/12/10 16:11
 * @Description:
 */
@Component
public class WeChatUtils {
    private OkHttpClient client = new OkHttpClient();

    public String getAccessToken() {
        try {
            return this.run("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APPID + "&secret=" + Constants.APPSECRET);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取网页授权凭证
    private String run(String url) throws IOException {
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
     * @return
     */
    public WeixinOauth2Token getOauth2AccessToken(){
        WeixinOauth2Token wat = null;
        JSONObject jsonObject=JSONObject.parseObject(getAccessToken());

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
            int errorCode = jsonObject.getInteger("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            e.printStackTrace();
        }
        return wat;
    }


}
