package com.example.freight.domain;

import org.springframework.util.StringUtils;

public class Stock {

    private String sku;
    private String color;
    private int stock;
    private int newStock;

    public String getNewSku() {
        if(!StringUtils.isEmpty(sku) && sku.contains("-")){
            return sku.substring(sku.indexOf("-") + 1);
        } else {
            return sku;
        }
    }

    public void setNewSku(String newSku) {
    }

    public int getNewStock() {
        return newStock;
    }

    public void setNewStock(int newStock) {
        this.newStock = newStock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "sku='" + sku + '\'' +
                ", color='" + color + '\'' +
                ", stock=" + stock +
                ", newStock=" + newStock +
                '}';
    }
}
