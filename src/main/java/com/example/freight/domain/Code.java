package com.example.freight.domain;

import lombok.Data;

/**
 * 邮编信息类
 * */
@Data
public class Code {

    /**
     * 目的地
     * */
    private String destination;

    /**
     * 判断邮编是否正常
     * */
    private boolean isNull = true;

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

}
