package com.signin.controller;

import com.signin.model.User;
import com.signin.model.WeixinOauth2Token;
import com.signin.service.UserInfoService;
import com.signin.utils.UserInfoUtil;
import com.signin.utils.WeChatUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Api(tags = {"微信接口相关"})
@RestController
public class WeChartController {

    @Autowired
    UserInfoService userInfoService;

    private static Logger logger= LoggerFactory.getLogger(WeChartController.class);


    /**
     * 确认请求来自微信服务器
     */
    @RequestMapping(value="/wechart/confirm",method= RequestMethod.GET)  // weixin/weixinOpe
    public void confirmMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WeChatUtils.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    /**
     * 微信回调
     * @param request
     * @param response
     */
    @RequestMapping(value="/wechart/callback",method= RequestMethod.GET)
    public void wechartCallback(HttpServletRequest request, HttpServletResponse response){
            logger.debug(" start to excute wechart callback");
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");

            // 用户同意授权后，能获取到code
            String code = request.getParameter("code");
            String state = request.getParameter("state");
            String openId="";

            // 用户同意授权
            if (code !=null && !"authdeny".equals(code)) {

                WeChatUtils weChatUtils = new WeChatUtils();
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = weChatUtils.getOauth2AccessToken(code);
                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                openId = weixinOauth2Token.getOpenId();

                logger.info("回调返回的accessToken:" + accessToken + ",openId:"
                        + openId);
                UserInfoUtil userInfoUtil=new UserInfoUtil();
                User user = userInfoService.getUser(openId,"");
                //
                request.getSession().setAttribute("userinfo",user);
                // 设置要传递的参数
                request.getSession().setAttribute("weixinOauth2Token", weixinOauth2Token);
                request.getSession().setAttribute("state", state);
            }

            logger.info("微信回调结束..........");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
