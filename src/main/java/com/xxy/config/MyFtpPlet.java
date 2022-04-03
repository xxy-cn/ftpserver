package com.xxy.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.ftplet.*;

import java.io.IOException;

@Slf4j
public class MyFtpPlet extends DefaultFtplet {

    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //获取上传文件的上传路径
        String path = session.getUser().getHomeDirectory();
        //获取上传用户
        String name = session.getUser().getName();
        //获取上传文件名
        String filename = request.getArgument();
        log.info("用户:'{}'，上传文件到目录：'{}'，文件名称为：'{}'，状态：开始上传~", name, path, filename);
        return super.onUploadStart(session, request);
    }

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //获取上传文件的上传路径
        String path = session.getUser().getHomeDirectory();
        //获取上传用户
        String name = session.getUser().getName();
        //获取上传文件名
        String filename = request.getArgument();
        log.info("用户:'{}'，上传文件到目录：'{}'，文件名称为：'{}，状态：成功！'", name, path, filename);
        return super.onUploadEnd(session, request);
    }

    @Override
    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return super.onDownloadEnd(session, request);
    }
}
