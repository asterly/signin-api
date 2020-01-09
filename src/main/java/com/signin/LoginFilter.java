package com.signin;


import com.signin.model.User;
import com.signin.model.WeixinOauth2Token;
import com.signin.utils.UserInfoUtil;
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

        String path = req.getRequestURI();
        logger.debug("路径："+path);
        // 登陆页面无需过滤
        if (path.indexOf("WxTokenAuthServlet.do") > -1 ||path.indexOf("ssologin")>-1||path.indexOf("csss")>-1
                || (req.getQueryString() !=null &&req.getQueryString().indexOf("methodName=login")>-1)) {
            chain.doFilter(request, response);
        } else {

            User userInfo = (User) session.getAttribute("userInfo");
            if(userInfo==null){
               //本地调试
                User user=new User();
                user.setId(10001L);
                user.setInvalid(1);
                user.setName("测试人员");
                user.setOpenid("4937BC8F45C794856AC265A85D003173");
                user.setRole("10001");
                ((HttpServletRequest) request).getSession().setAttribute("userInfo", user);

            }else{
                //生产环境使用
                User user = new UserInfoUtil().ParseUser();
                ((HttpServletRequest) request).getSession().setAttribute("userInfo", user);
//                String url = "";
//                res.sendRedirect(url);
            }

            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig conf) throws ServletException {

    }

    public void destroy() {
    }



}