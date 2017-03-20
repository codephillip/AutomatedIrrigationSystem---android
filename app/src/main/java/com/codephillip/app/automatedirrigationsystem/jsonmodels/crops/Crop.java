
package com.codephillip.app.automatedirrigationsystem.jsonmodels.crops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crop {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("optimal_water_level")
    @Expose
    private Double optimalWaterLevel;
    @SerializedName("crop_type")
    @Expose
    private String cropType;

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

    public Double getOptimalWaterLevel() {
        return optimalWaterLevel;
    }

    public void setOptimalWaterLevel(Double optimalWaterLevel) {
        this.optimalWaterLevel = optimalWaterLevel;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

}
