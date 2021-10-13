package com.example.freight.service.Impl;

import com.example.freight.domain.ResultData;
import com.example.freight.domain.User;
import com.example.freight.mapper.UserMapper;
import com.example.freight.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 后台登陆验证
     * */
    @Override
    public ResultData userLogin(String userName, String password) {

        //md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user = userMapper.userLogin(userName, password);

        ResultData resultData = new ResultData();

        resultData.setCode(user == null ? 0 : 1);
        resultData.setMsg(user == null ? "Login failed" : "Login succeeded");
        return resultData;
    }
}
