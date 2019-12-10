package com.signin.utils;

import com.signin.common.Constants;
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

    private String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return Objects.requireNonNull(response.body()).string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
