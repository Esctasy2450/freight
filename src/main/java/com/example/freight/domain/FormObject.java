package com.example.freight.domain;

import lombok.Data;

import java.util.List;

@Data

/**
 * 用于接收表单数据
 * */
public class FormObject {

    /**
     * 邮编
     * */
    private String zipCode;

    /**
     * 是否组装标志
     * */
    private boolean type;

    /**
     * 接收表单数组
     * */
    private List<Domains> domains;

    /**
     *
     * */
    private int code;

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

}
