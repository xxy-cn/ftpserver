package com.xxy.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.DbUserManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration("FtpServerControl")
public class FtpServerControl {

    @Autowired
    private DataSource dataSource;

    protected FtpServer server;

    /*public FtpServerControl(DataSource dataSource) {
        this.dataSource = dataSource;
        initFtp();
        log.info("Apache ftp server is already instantiation complete!");

    }*/

    public void initFtp(int port) {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        //1、设置服务端口
        listenerFactory.setPort(port);
        //2、设置被动模式数据上传的接口范围,云服务器需要开放对应区间的端口给客户端
        DataConnectionConfigurationFactory dataConnectionConfFactory = new DataConnectionConfigurationFactory();
        dataConnectionConfFactory.setPassivePorts("10000-10500");
        listenerFactory.setDataConnectionConfiguration(dataConnectionConfFactory.createDataConnectionConfiguration());
        //3、增加SSL安全配置
        //SslConfigurationFactory ssl = new SslConfigurationFactory();
        //ssl.setKeystoreFile(new File("src/main/resources/ftpserver.jks"));
        //ssl.setKeystorePassword("990815@XXy");
        //ssl.setSslProtocol("TLS");
        // set the SSL configuration for the listener
        //listenerFactory.setSslConfiguration(ssl.createSslConfiguration());
        //listenerFactory.setImplicitSsl(true);
        //4、替换默认的监听器
        Listener listener = listenerFactory.createListener();
        serverFactory.addListener("FTP-SERVER", listener);
        //5、配置自定义用户事件
        Map<String, Ftplet> ftpLets = new HashMap();
        ftpLets.put("ftpService", new MyFtpPlet());
        serverFactory.setFtplets(ftpLets);
        //6、读取用户的配置信息
        //注意：配置文件位于resources目录下，如果项目使用内置容器打成jar包发布，FTPServer无法直接直接读取Jar包中的配置文件。
        //解决办法：将文件复制到指定目录(本文指定到根目录)下然后FTPServer才能读取到。
//        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//        String tempPath = System.getProperty("java.io.tmpdir") + System.currentTimeMillis() + ".properties";
//        File tempConfig = new File(tempPath);
//        ClassPathResource resource = new ClassPathResource("users.properties");
//        IOUtils.copy(resource.getInputStream(), new FileOutputStream(tempConfig));
//        userManagerFactory.setFile(tempConfig);
//        userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());  //密码以明文的方式
//        serverFactory.setUserManager(userManagerFactory.createUserManager());
        //6、基于数据库来存储用户实例
        DbUserManagerFactory dbUserManagerFactory = new DbUserManagerFactory();
        dbUserManagerFactory.setDataSource(dataSource);
        dbUserManagerFactory.setAdminName("admin");
        dbUserManagerFactory.setSqlUserAdmin("SELECT userid FROM FTP_USER WHERE userid='{userid}' AND userid='admin'");
        dbUserManagerFactory.setSqlUserInsert("INSERT INTO FTP_USER (userid, userpassword, homedirectory, " +
                "enableflag, writepermission, idletime, uploadrate, downloadrate) VALUES " +
                "('{userid}', '{userpassword}', '{homedirectory}', {enableflag}, " +
                "{writepermission}, {idletime}, uploadrate}, {downloadrate})");
        dbUserManagerFactory.setSqlUserDelete("DELETE FROM FTP_USER WHERE userid = '{userid}'");
        dbUserManagerFactory.setSqlUserUpdate("UPDATE FTP_USER SET userpassword='{userpassword}',homedirectory='{homedirectory}',enableflag={enableflag},writepermission={writepermission},idletime={idletime},uploadrate={uploadrate},downloadrate={downloadrate},maxloginnumber={maxloginnumber}, maxloginperip={maxloginperip} WHERE userid='{userid}'");
        dbUserManagerFactory.setSqlUserSelect("SELECT * FROM FTP_USER WHERE userid = '{userid}' id_delete = 0");
        dbUserManagerFactory.setSqlUserSelectAll("SELECT userid FROM FTP_USER ORDER BY userid");
        dbUserManagerFactory.setSqlUserAuthenticate("SELECT userid, userpassword FROM FTP_USER WHERE userid='{userid}' and id_delete = 0");
        dbUserManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        serverFactory.setUserManager(dbUserManagerFactory.createUserManager());
        //7、实例化FTP Server
        server = serverFactory.createServer();
    }

    public void start(DataSource dataSource,int port){

        log.info("Apache ftp server is already instantiation complete!");
        this.dataSource = dataSource;
        initFtp(port);
        try {
            server.start();
            log.info("Apache Ftp server is starting!");
        }catch(FtpException e) {
            e.printStackTrace();
        }
    }


    /**
     * ftp server stop
     */
    public void stop() {
        server.stop();
        log.info("Apache Ftp server is stoping!");
    }



}
