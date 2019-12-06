package com.signin.common;

/**
 * @Auther: engow
 * @Date: 2019-02-28 18:48
 * @Description:
 */
public interface Constants {
    // 百度语音合成
    String BAIDU_APP_ID = "11711194";
    String BAIDU_APP_KEY = "XEMQKmnhwIzouXMi1OkwFLe8";
    String BAIDU_SECRET_KEY = "jUWu9ikIEEg3pnNd2qCGfxH2MMYc4ihs";

    // 语音文件格式
    String VOICE_FORMAT = ".mp3";

    String SERVER_FILE_DIR = "/usr/share/nginx/html/resource/itrip/voice/";
    String DEVELOPER_FILE_DIR = "C:\\Users\\ChengChow\\Downloads\\";
    String FILE_DIR = SERVER_FILE_DIR;

    String IMAGE_DIR = "/usr/share/nginx/html/resource/itrip/image/";
    String RESOURCE_DIR = "/usr/share/nginx/html/resource/itrip/";

    String RESOURCE_URL = "https://resource.wuhansoftware.com/itrip/";

    String APPID = "wxa959ded0d5fe423d";
    String APPSECRET = "2c4939f7fb2cb86cb7ecf9672881bd81";

    String CODE_TO_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", APPSECRET);

    Integer SMS_APPID = 1400160806;
    String SMS_APPKEY = "e8e3a31f99bb8e1b5dd723a81c68a2ea";
    Integer SMS_TEMPLATEID = 230898;
    String SMS_SIGN = "心溪网络";
    String SMS_WAITTIME = "60";
}
