package com.xxy.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Value;

import java.io.Serializable;

/**
 * 
 * @TableName FTP_USER
 */
@Value
@TableName("FTP_USER")
public class FtpUser implements Serializable {
    /**
     * 用户名
     */
    @TableId
    private String userid;

    /**
     * 密码
     */
    private String userpassword;

    /**
     * 家目录
     */
    private String homedirectory;

    /**
     * 是否启用
     */
    private Integer enableflag;

    /**
     * 写权限
     */
    private Integer writepermission;

    /**
     * 空闲时间
     */
    private Integer idletime;

    /**
     * 上传速率
     */
    private Integer uploadrate;

    /**
     * 下载速率
     */
    private Integer downloadrate;

    /**
     * 最大登陆用户数
     */
    private Integer maxloginnumber;

    /**
     * 同IP登陆用户数
     */
    private Integer maxloginperip;

    /**
    * 是否删除
    */
    private String is_delete;

    private static final long serialVersionUID = 1L;
}