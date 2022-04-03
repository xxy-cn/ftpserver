package com.xxy.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xxy.annotion.LoginToken;
import com.xxy.annotion.PassToken;
import com.xxy.bean.FtpUser;
import com.xxy.config.JwtTokenConfig;
import com.xxy.service.impl.FtpUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenConfig jwtTokenConfig;
    @Autowired
    private FtpUserServiceImpl ftpUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlermethod = (HandlerMethod) handler;
        Method method = handlermethod.getMethod();
        //如果有PassToken注解直接放行
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //如果有LoginToken注解
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                //认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                String userId = "";
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                //获取用户信息
                FtpUser user = ftpUserService.selectUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在,请重新登录");
                }
                //验证jwt
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserpassword())).build();
                try{
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
