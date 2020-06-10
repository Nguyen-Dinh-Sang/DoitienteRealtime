package com.ptuddd.doitien.model;

public class CurrencyModel {
    private String name;
    //Rate so với usd
    private Double rate;

    public CurrencyModel(String name, Double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
