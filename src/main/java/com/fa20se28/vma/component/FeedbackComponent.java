package com.fa20se28.vma.component;

import com.fa20se28.vma.model.Feedback;
import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;

import java.util.List;

public interface FeedbackComponent {
    int insertFeedback(FeedbackReq feedbackReq, String ownerId);

    void updateFeedback(FeedbackReq feedbackReq);

    void deleteFeedback(int feedbackId);

    int getTotalFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption);

    List<Feedback> getFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption, int pageNum);
}
