
package com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Metrics {

    @SerializedName("metrics")
    @Expose
    private List<Metric> metrics = null;

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

}
