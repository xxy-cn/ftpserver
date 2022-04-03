package com.xxy.service;

import com.xxy.bean.FtpUser;

/**
* @param:
* @return:
* @author: xxy
* @date: 2022/4/3
* @description: FtpUser业务类
*/
public interface FtpUserService {

    /**
    * @param: [id]
    * @return: com.xxy.bean.FtpUser
    * @author: xxy
    * @date: 2022/4/3
    * @description: 根据id获取用户信息
    */
    FtpUser selectUserById(String id);

    /**
    * @param: [ftpUser]
    * @return: int
    * @author: xxy
    * @date: 2022/4/3
    * @description: 更新用户信息
    */
    int updateUser(FtpUser ftpUser);

    /**
    * @param: [id]
    * @return: int
    * @author: xxy
    * @date: 2022/4/3
    * @description: 将用户标志为删除
    */
    int deleteUser(FtpUser ftpUser);

    /**
    * @param: [ftpUser]
    * @return: int
    * @author: xxy
    * @date: 2022/4/3
    * @description: 添加一个用户
    */
    int insertUser(FtpUser ftpUser);

}
