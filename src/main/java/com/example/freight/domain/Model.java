package com.example.freight.domain;

public class Model {
    private int id;
    private String sku;
    private int type;
    private int groupNum;
    private int weight;
    private int length;
    private int width;
    private int height;
    private double ordinary;
    private double volume;
    private double vLength;
    private double vWidth;
    private double vHeight;
    private int stock = 0;
    private int newStock;
    private String color;

    public String getColor() {
        return color;
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

    //  超长标识符
    private boolean isSize;


    public boolean isSize() {
        return isSize;
    }

    public void setSize(boolean size) {
        isSize = size;
    }


    public void setOrdinary(double ordinary) {
        this.ordinary = ordinary;
    }

    public double getvLength() {
        return vLength;
    }

    public void setvLength(double vLength) {
        this.vLength = vLength;
    }

    public double getvWidth() {
        return vWidth;
    }

    public void setvWidth(double vWidth) {
        this.vWidth = vWidth;
    }

    public double getvHeight() {
        return vHeight;
    }

    public void setvHeight(double vHeight) {
        this.vHeight = vHeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(int ordinary) {
        this.ordinary = ordinary;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", type=" + type +
                ", groupNum=" + groupNum +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", ordinary=" + ordinary +
                ", volume=" + volume +
                ", vLength=" + vLength +
                ", vWidth=" + vWidth +
                ", vHeight=" + vHeight +
                ", stock=" + stock +
                ", isSize=" + isSize +
                '}';
    }
}
