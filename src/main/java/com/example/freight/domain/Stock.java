package com.example.freight.domain;

import lombok.Data;

@Data

/**
 * 库存信息类
 * */
public class Stock {

    /**
     * 型号
     * */
    private String sku;

    /**
     * 颜色
     * */
    public String color;

    /**
     * 库存
     * */
    public int stock;

    /**
     * 新库存
     * */
    public int newStock;

}
