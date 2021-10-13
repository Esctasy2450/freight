package com.example.freight.service;

import com.example.freight.domain.ResultData;
import com.example.freight.domain.User;

public interface IUserService {

    /**
     * 后台登陆验证
     * */
    ResultData userLogin(String userName, String password);
}
