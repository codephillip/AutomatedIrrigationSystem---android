
package com.codephillip.app.automatedirrigationsystem.jsonmodels.feedbacks;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedbacks {

    @SerializedName("feedbacks")
    @Expose
    private List<Feedback> feedbacks = null;

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

}
