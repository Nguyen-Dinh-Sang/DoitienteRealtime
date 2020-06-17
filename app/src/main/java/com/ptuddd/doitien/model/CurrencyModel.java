package com.ptuddd.doitien.model;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public class CurrencyModel {
    private String name;
    //Rate so với usd
    private double rate;
    private String sysbol;

    public CurrencyModel(String name, double rate , String sysbol) {
        this.name = name;
        this.rate = rate;
        this.sysbol =sysbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getSysbol() {
        return sysbol;
    }

    public void setSysbol(String sysbol) {
        this.sysbol = sysbol;
    }

    @NonNull
    @Override
    public String toString() {
        return sysbol;
    }
}
