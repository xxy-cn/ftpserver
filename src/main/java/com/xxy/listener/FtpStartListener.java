package com.xxy.listener;

import com.xxy.config.FtpServerControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class FtpStartListener implements ApplicationRunner {
    @Autowired
    private FtpServerControl server;
    @Autowired
    private DataSource dataSource;
    @Value("${ftpserver.port}")
    int port;

    @Override
    public void run(ApplicationArguments args) {
        try {
            //项目启动时已经加载好了
            server.start(dataSource,port);
            log.info("Apache Ftp server is started!");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Apache Ftp server start failed!", e);
        }
    }
}
