package com.example.freight.domain;

import java.util.List;

public class FormObject {
    private String zipCode;
    private boolean type;
    private List<Domains> domains;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getZipCode() {
        return zipCode;
    }

    public List<Domains> getDomains() {
        return domains;
    }

    public void setDomains(List<Domains> domains) {
        this.domains = domains;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "FormObject{" +
                "zipCode='" + zipCode + '\'' +
                ", type=" + type +
                ", domains=" + domains +
                ", code=" + code +
                '}';
    }
}
