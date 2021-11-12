package com.example.freight.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class Domains extends Stock {

    /**
     * 型号
     * */
    private String sku;

    /**
     * 数量
     * */
    private int num;

    /**
     * 简写型号
     * */
    private String newSku;

    public String getColor() {
        if(!StringUtils.isEmpty(sku) && sku.contains("-")) {
            return sku.substring(0,sku.indexOf("-"));
        }else {
            return null;
        }
    }

    public String getNewSku() {
        if(!StringUtils.isEmpty(sku) && sku.contains("-")){
            return sku.substring(sku.indexOf("-") + 1);
        } else {
            return sku;
        }
    }
}
