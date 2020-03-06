package com.signin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.signin.common.ResultData;
import com.signin.common.Constants;
import com.signin.model.User;
import com.signin.model.WeixinOauth2Token;
import com.signin.service.UserInfoService;
import com.signin.service.impl.WechatServiceImpl;
import com.signin.utils.UserInfoUtil;
import com.signin.utils.WeChatUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Api(tags = {"微信接口相关"})
@RestController
public class WeChartController {

    @Autowired
    UserInfoService userInfoService;



    @Value("${signapi.signin-reg-url:nothing}")
    private String redirectURL;

    private static Logger logger= LoggerFactory.getLogger(WeChartController.class);


    /**
     * 确认请求来自微信服务器
     */
    @CrossOrigin
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
     * 接收微信客户端发来的消息
     * @param request
     * @param response
     */
    @CrossOrigin
    @RequestMapping(value = "/wechart/confirm", method = RequestMethod.POST)
    public void wechatServicePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WechatServiceImpl wechatService=new WechatServiceImpl();
        String responseMessage = wechatService.processRequest(request);
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(JSONObject.toJSONString(parameterMap));
        System.out.println(responseMessage);

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        //回写用户消息
        writer.print(responseMessage);
        writer.flush();
    }

    /**
     * 微信回调
     * @param request
     * @param response
     */
    @CrossOrigin
    @RequestMapping(value="/wechart/callback",method= RequestMethod.GET)
    public void wechartCallback(HttpServletRequest request, HttpServletResponse response){
            logger.debug(" start to excute wechart callback");
        try {

            System.out.println(redirectURL);
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");

            // 用户同意授权后，能获取到code
            String code = request.getParameter("code");
            //String state = request.getParameter("state");
            String openId="";

            // 用户同意授权
            if (code !=null && !"authdeny".equals(code)) {

                WeChatUtils weChatUtils = new WeChatUtils();
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = weChatUtils.getOauth2AccessToken(code);

                if(weixinOauth2Token==null){
                    logger.debug("获取微信接口数据失败......");
                    return;
                }

                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                openId = weixinOauth2Token.getOpenId();

                logger.info("回调返回的accessToken:" + accessToken + ",openId:"
                        + openId);

                User user = userInfoService.getUser(openId);

                if(user==null&&openId!=null){
                    //用户授权之后，得到openID 但没有关联数据的数据，
                    //或者没有数据，则需要跳转去执行关联
                    request.getSession().setAttribute("opeinID",openId);
                    //重定向到关联openid页面
                    response.sendRedirect(redirectURL);
                    return;
                }

                request.getSession().setAttribute("userInfo",user);
                // 设置要传递的参数
//                request.getSession().setAttribute("weixinOauth2Token", weixinOauth2Token);
//                request.getSession().setAttribute("state", state);
            }

            logger.info("微信回调结束..........");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @RequestMapping(value="/wechart/user",method= RequestMethod.GET)
    public String pareseByOpenid(HttpServletRequest request, HttpServletResponse response){
        String opeinID = request.getSession().getAttribute("opeinID").toString();

        String redirectUrl = request.getSession().getAttribute("redirectUrl").toString();

        User user = userInfoService.getUser(opeinID);
        request.getSession().setAttribute("userInfo",user);

        return "redirect:"+redirectUrl;


    }

    @CrossOrigin
    @RequestMapping(value="/wechart/controller")
    public String wechatController(HttpServletRequest request, HttpServletResponse response){

        //获取微信code
        try {
            response.sendRedirect(Constants.WECHART_AUTHER_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }



    /**
     * 微信回调
     * @param request
     * @param response
     */
    @CrossOrigin
    @RequestMapping(value="/wechart/authtoken",method= RequestMethod.GET)
    public String wechartAuthToken(HttpServletRequest request, HttpServletResponse response){
        logger.debug(" start to excute wechart callback");
        String openId="";
        User user=null;
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            // 用户同意授权后，能获取到code
            String code = request.getParameter("code");
            String state = request.getParameter("state");


            // 用户同意授权
            if (code !=null && !"authdeny".equals(code)) {

                WeChatUtils weChatUtils = new WeChatUtils();
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = weChatUtils.getOauth2AccessToken(code);

                if(weixinOauth2Token==null){
                    logger.debug("获取微信接口数据失败......");
                }

                // 网页授权接口访问凭证
                String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                openId = weixinOauth2Token.getOpenId();

                logger.info("回调返回的 accessToken:" + accessToken + ",openId:"
                        + openId);

                user = userInfoService.getUser(openId);

                if(user==null&&openId!=null){
                    System.out.println(redirectURL);
                    //用户授权之后，得到openID 但没有关联数据的数据，
                    //或者没有数据，则需要跳转去执行关联
                    request.getSession().setAttribute("opeinID",openId);
                    //重定向到关联openid页面
                    response.sendRedirect("重定向的地址");
                    return "redirect:"+redirectURL;
                }

                request.getSession().setAttribute("userInfo",user);
                // 设置要传递的参数
//                request.getSession().setAttribute("weixinOauth2Token", weixinOauth2Token);
//                request.getSession().setAttribute("state", state);
            }

            logger.info("微信回调结束..........");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultData.success(user);
    }


}
