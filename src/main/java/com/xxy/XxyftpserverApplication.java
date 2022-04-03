package com.xxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xxy.mapper")
public class XxyftpserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxyftpserverApplication.class, args);
    }
}
