package com.signin;


import com.signin.common.Constants;
import com.signin.model.User;
import com.signin.model.WeixinOauth2Token;
import com.signin.utils.UserInfoUtil;
import com.signin.utils.WeChatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public LoginFilter() {
        super();
    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.debug("-----------------拦截请求------------------------------");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("P3P","CP=CAO PSA OUR");
        HttpSession session = req.getSession(true);

        String ipAddress = WeChatUtils.getIpAddress(req);
        System.out.println(ipAddress);
        String path = req.getRequestURI();
        System.out.println("路径："+path);
        // 登陆页面无需过滤
        if (Constants.open_weixin_qq_com.equalsIgnoreCase(ipAddress)||path.indexOf("ssologin")>-1||path.indexOf("csss")>-1
                || (req.getQueryString() !=null &&req.getQueryString().indexOf("methodName=login")>-1)) {
            chain.doFilter(request, response);


        } else {

            User userInfo = (User) session.getAttribute("userInfo");
            if(userInfo==null){
               //本地调试
//                User user=new User();
//                user.setId(100001L);
//                user.setInvalid(0);
//                user.setName("测试人员");
//                user.setOpenid("4937BC8F45C794856AC265A85D003173");
//                user.setRoleId(100001);
//                ((HttpServletRequest) request).getSession().setAttribute("userInfo", user);

                //获取微信code
                res.sendRedirect(Constants.WECHART_AUTHER_URL);


            }

            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig conf) throws ServletException {

    }

    public void destroy() {
    }



}