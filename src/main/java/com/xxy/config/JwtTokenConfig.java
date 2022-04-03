package com.xxy.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.xxy.bean.FtpUser;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
* @param:
* @return:
* @author: xxy
* @date: 2022/4/3
* @description: token生成
*/
@Configuration
public class JwtTokenConfig {

    //过期时间5分钟
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    public String generateToken(FtpUser ftpUser) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = "";
        token = JWT.create().withAudience(ftpUser.getUserid())     //将id保存到token
                .withExpiresAt(date)  //5分钟过期
                .sign(Algorithm.HMAC256(ftpUser.getUserpassword()));   // 以 password 作为 token 的密钥
        return token;
    }
}
