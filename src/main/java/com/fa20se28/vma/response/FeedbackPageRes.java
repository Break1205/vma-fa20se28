package com.fa20se28.vma.response;

import com.fa20se28.vma.model.Feedback;

import java.util.List;

public class FeedbackPageRes {
    private List<Feedback> feedbackList;

    public FeedbackPageRes(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
