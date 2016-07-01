package com.halilibrahimaksoy.borsaalarm.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by halil ibrahim aksoy on 5.04.2016.
 */
public class FollowExchange extends RealmObject implements Serializable {

    private String name;
    private Date createDate;


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
}
