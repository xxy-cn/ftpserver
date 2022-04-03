package com.xxy.controller;

import com.xxy.bean.FtpUser;
import com.xxy.service.impl.FtpUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FtpUserController {

    @Autowired
    private FtpUserServiceImpl ftpUserService;

    @GetMapping("/deleteUser")
    public String deleteUser() {
        FtpUser user = ftpUserService.selectUserById("test04");
        log.info(user.toString());
        ftpUserService.deleteUser(user);
        return "ok";
    }

}
