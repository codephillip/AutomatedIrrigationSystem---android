
package com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("water_volume")
    @Expose
    private Double waterVolume;
    @SerializedName("isIrrigating")
    @Expose
    private Boolean isIrrigating;
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(Double waterVolume) {
        this.waterVolume = waterVolume;
    }

    public Boolean getIsIrrigating() {
        return isIrrigating;
    }

    public void setIsIrrigating(Boolean isIrrigating) {
        this.isIrrigating = isIrrigating;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
