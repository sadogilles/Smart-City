package com.smart.smartcity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TradeType {
    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("children")
    private List<Integer> children;

    @Expose
    @SerializedName("tradeId")
    private Integer tradeId;

    private boolean cancel;

    public static TradeType createCancelObject() {
        TradeType result = new TradeType();

        result.name = "Cancel";
        result.cancel = true;
        result.id = -2;

        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getChildren() {
        return children;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public boolean isCancel() {
        return cancel;
    }
}
