package com.smart.smartcity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("userId")
    private int userId;

    @Expose
    @SerializedName("networkId")
    private int networkId;

    @Expose
    @SerializedName("state")
    private String state;

    @Expose
    @SerializedName("userName")
    private String userName;

    public Subscription(int userId, int networkId, String state) {
        this.userId = userId;
        this.networkId = networkId;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
