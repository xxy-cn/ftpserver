package com.xxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxy.bean.FtpUser;
import com.xxy.mapper.FtpUserMapper;
import com.xxy.service.FtpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FtpUserServiceImpl implements FtpUserService {

    @Autowired
    protected FtpUserMapper ftpUserMapper;

    @Override
    public FtpUser selectUserById(String id) {
        return ftpUserMapper.selectOne(new QueryWrapper<FtpUser>().eq("userid",id).eq("is_delete",0));
    }

    @Override
    public int updateUser(FtpUser ftpUser) {

        return ftpUserMapper.updateById(ftpUser);
    }

    @Override
    public int deleteUser(FtpUser ftpUser) {
        return ftpUserMapper.update(ftpUser,new UpdateWrapper<FtpUser>().set("is_delete",1).eq("userid",ftpUser.getUserid()));
    }

    @Override
    public int insertUser(FtpUser ftpUser) {
        return ftpUserMapper.insert(ftpUser);
    }
}
