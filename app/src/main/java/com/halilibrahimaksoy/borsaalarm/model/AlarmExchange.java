package com.halilibrahimaksoy.borsaalarm.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by halil ibrahim aksoy on 14.04.2016.
 */
public class AlarmExchange extends RealmObject implements Serializable {

    private String name;
    private Date createDate;
    private float price;
    private float minPrice;
    private float maxPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
