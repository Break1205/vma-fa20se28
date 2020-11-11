package com.fa20se28.vma.component;

import com.fa20se28.vma.request.FeedbackReq;

public interface FeedbackComponent {
    int insertFeedback(FeedbackReq feedbackReq, String ownerId);

    void updateFeedback(FeedbackReq feedbackReq);

    void deleteFeedback(int feedbackId);
}
