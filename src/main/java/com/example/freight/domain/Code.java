package com.example.freight.domain;

public class Code {

    private int id;
    private String destination;
    private int min;
    private int max;
    private boolean isNull = true;

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "code{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
