package com.example.freight.domain;

import org.springframework.util.StringUtils;

public class Domains {

    private String sku ;
    private int num;
    private int stock;
    private int newStock;
    private String newSku;
    private String color;

    public String getColor() {
        if(!StringUtils.isEmpty(sku) && sku.contains("-")) {
            return sku.substring(0,sku.indexOf("-"));
        }else {
            return null;
        }
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNewStock() {
        return newStock;
    }

    public void setNewStock(int newStock) {
        this.newStock = newStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setNewSku(String newSku) {
        this.newSku = newSku;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNewSku() {
        if(!StringUtils.isEmpty(sku) && sku.contains("-")){
            return sku.substring(sku.indexOf("-") + 1);
        } else {
            return sku;
        }
    }


    @Override
    public String toString() {
        return "Domains{" +
                "sku='" + sku + '\'' +
                ", num=" + num +
                ", stock=" + stock +
                ", newSku='" + newSku + '\'' +
                ", color='" + color + '\'' +
                ", newStock=" + newStock +
                '}';
    }
}
