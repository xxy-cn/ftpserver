package com.xxy.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xxy.annotion.LoginToken;
import com.xxy.annotion.PassToken;
import com.xxy.bean.FtpUser;
import com.xxy.config.JwtTokenConfig;
import com.xxy.service.impl.FtpUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FtpUserController {

    @Autowired
    private FtpUserServiceImpl ftpUserService;
    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @GetMapping("/deleteUser")
    public String deleteUser() {
        FtpUser user = ftpUserService.selectUserById("test04");
        log.info(user.toString());
        ftpUserService.deleteUser(user);
        return "ok";
    }

    @PostMapping("/login")
    public Map login(@RequestBody String body) {
        Map<String,Object> map = new HashMap<>();
        JSONObject jsonObject = JSONUtil.parseObj(body);
        FtpUser ftpUser = ftpUserService.selectUserById(jsonObject.getStr("username"));
        if (ftpUser != null && ftpUser.getUserpassword().equals(SecureUtil.md5(jsonObject.getStr("password")))) {
            String token = jwtTokenConfig.generateToken(ftpUser);
            map.put("user",ftpUser);
            map.put("token",token);
        }else {
            map.put("result","用户名或密码错误");
        }
        return map;
    }

    @PassToken
    @PostMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }


}
