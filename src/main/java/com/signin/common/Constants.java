package com.signin.common;

/**
 * @Auther: engow
 * @Date: 2019-02-28 18:48
 * @Description:
 */
public interface Constants {
    // 微信公众平台-心溪流声
//    String APPID = "wx3faac60b00aca54f";
//    String APPSECRET = "8bed2a4ae3b32d693068de08b1d30fb7";
    String APPID = "wxb3c22ba36aae943c";
    String APPSECRET = "f3c70f4323e107a46a5894255722edc5";

    String SERVER_FILE_DIR = "/usr/share/nginx/html/resource/itrip/voice/";
    String DEVELOPER_FILE_DIR = "C:\\Users\\ChengChow\\Downloads\\";
    String FILE_DIR = SERVER_FILE_DIR;

    String IMAGE_DIR = "/usr/share/nginx/html/resource/itrip/image/";
    String RESOURCE_DIR = "/usr/share/nginx/html/resource/itrip/";

    String RESOURCE_URL = "https://resource.wuhansoftware.com/itrip/";

    //项目回调地址 nat app authtoken 	2b440fc2b5d2ecf3
    String CALLBACK_URL="http://signapi2.natapp1.cc/v1/api/wechart/callback";

    //查询微信code
    String WECHART_AUTHER_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constants.APPID+"&redirect_uri="+Constants.CALLBACK_URL+"&response_type=code&scope=snsapi_userinfo&state=wechartcallback#wechat_redirect";

    //查询微信OPENID
    String WECHART_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constants.APPID+"&secret="+Constants.APPSECRET+"&code=REQUEST_CODE&grant_type=authorization_code";

    String  WECHAT_CALLBACK_FLAG="wechartcallback";

}
