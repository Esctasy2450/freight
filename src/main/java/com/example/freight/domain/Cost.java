package com.example.freight.domain;

import lombok.Data;

@Data

/**
 * 费用信息类
 * */
public class Cost {

    /**
     * 主键
     * */
    private int id;

    /**
     * 目的地简称
     * */
    private String name;

    /**
     * 重量
     * */
    private int weight;

    /**
     * 费用
     * */
    private double price;

    /**
     * 标记托盘数量
     * */
    private int i;

    /**
     * 标记计费档位
     * */
    private int s;

}
