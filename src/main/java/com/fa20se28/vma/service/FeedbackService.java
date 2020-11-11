package com.fa20se28.vma.service;

import com.fa20se28.vma.request.FeedbackReq;

public interface FeedbackService {
    int createFeedback(FeedbackReq feedbackReq);

    void updateFeedback(FeedbackReq feedbackReq);

    void deleteFeedback(int feedbackId);
}
