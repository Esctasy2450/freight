package com.example.freight.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

/**
 * 型号信息类
 * */
public class Model extends Stock {

    /**
     * 主键
     * */
    private int id;

    /**
     * 型号
     * */
    private String sku;

    /**
     * 是否可以组装
     * */
    private int type;

    /**
     * 一组里件数的数量
     * */
    private int groupNum;

    /**
     * 重量
     * */
    private int weight;

    /**
     * 打包长度
     * */
    private int length;

    /**
     * 打包宽度
     * */
    private int width;

    /**
     * 打包高度
     * */
    private int height;

    /**
     * 打包体积
     * */
    private double ordinary;

    /**
     * 组装后的体积
     * */
    private double volume;

    /**
     * 组装后的长
     * */
    private double vLength;

    /**
     * 组装后的宽
     * */
    private double vWidth;

    /**
     * 组装后的高
     * */
    private double vHeight;

    /**
     * 超长标识符
     * */
    private boolean isSize;

    public boolean isSize() {
        return isSize;
    }

    public void setSize(boolean size) {
        isSize = size;
    }
}
