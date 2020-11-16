package com.fa20se28.vma.service;

import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;
import com.fa20se28.vma.response.FeedbackPageRes;

public interface FeedbackService {
    int createFeedback(FeedbackReq feedbackReq);

    void updateFeedback(FeedbackReq feedbackReq);

    void deleteFeedback(int feedbackId);

    int getTotalFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption);

    FeedbackPageRes getFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption, int pageNum);
}
