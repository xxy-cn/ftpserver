package com.xxy.xxyftpserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan("com.xxy.*")
@ComponentScan("com.xxy.*")
@MapperScan("com.xxy.mapper")
public class XxyftpserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxyftpserverApplication.class, args);
    }
}
