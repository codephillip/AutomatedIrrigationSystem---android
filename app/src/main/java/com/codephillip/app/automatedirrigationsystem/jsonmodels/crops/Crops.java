
package com.codephillip.app.automatedirrigationsystem.jsonmodels.crops;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crops {

    @SerializedName("crops")
    @Expose
    private List<Crop> crops = null;

    public List<Crop> getCrops() {
        return crops;
    }

    public void setCrops(List<Crop> crops) {
        this.crops = crops;
    }

}
