package com.example.freight.domain;

import lombok.Data;

@Data

/**
 * 标准返回类
 * */
public class ResultData {

    /**
     * 状态码
     * */
    private int code;

    /**
     * 信息
     * */
    private String msg;

    /**
     * 数据
     * */
    private Object data;

}
