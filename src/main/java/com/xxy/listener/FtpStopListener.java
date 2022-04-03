package com.xxy.listener;

import com.xxy.config.FtpServerControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class FtpStopListener implements ServletContextListener {

    private static final String SERVER_NAME="FTP-SERVER";

    @Autowired
    private FtpServerControl server;
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        server.stop();
        sce.getServletContext().removeAttribute(SERVER_NAME);
        log.info("Apache Ftp server is stoped!");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
