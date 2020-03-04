package com.signin.utils;

import com.alibaba.fastjson.JSON;
import com.signin.dao.UserDao;
import com.signin.model.User;
import com.signin.model.WeixinOauth2Token;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoUtil {

    @Autowired
    private UserDao userDao;


    /**
     * 解析前台传过来的参数
     * @param request
     * @return
     */
    public static Map parseParams(HttpServletRequest request){
        Map params = new HashMap();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));

            if(paraName.equals("params")){
                Map paramMap=JSON.parseObject(request.getParameter(paraName), Map.class);
                params.putAll(paramMap);
            }else{
                params.put(paraName, request.getParameter(paraName));
            }

        }

        return  params;
    }

    /**
     * 将用户数据写入参数集合中
     * @param request
     * @param params
     * @return
     */
    public static User parseUser(HttpServletRequest request,Map params){
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        if(userInfo != null){
            params.put("userId", userInfo.getId());
            params.put("openid", userInfo.getOpenid());
            params.put("name", userInfo.getName());
            params.put("roleId",userInfo.getRoleId());
        }

        return userInfo;
    }

    /**
     *  调用微信端
     * @return
     */
    public  User ParseUser(){
        WeixinOauth2Token oauth2AccessToken = new WeChatUtils().getOauth2AccessToken("");
        String openId = oauth2AccessToken.getOpenId();
        List<User> users = userDao.selUserByOpenID(openId,"100003");
        return users.get(0);
    }


    public  User ParseUserByOpenID(String openid ){
        List<User> users = userDao.selUserByOpenID(openid,"100003");
        return users.get(0);
    }

}
