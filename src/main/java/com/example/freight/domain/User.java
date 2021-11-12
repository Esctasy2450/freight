package com.example.freight.domain;

import lombok.Data;

@Data

/**
 * 用户信息类
 * */
public class User {

    /**
     * 主键
     * */
    private int id;

    /**
     * 用户名
     * */
    private String userName;

    /**
     * 密码
     * */
    private String password;

}
