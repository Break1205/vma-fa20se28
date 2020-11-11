package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.FeedbackComponent;
import com.fa20se28.vma.mapper.FeedbackMapper;
import com.fa20se28.vma.request.FeedbackReq;
import org.springframework.stereotype.Component;

@Component
public class FeedbackComponentImpl implements FeedbackComponent {
    private final FeedbackMapper feedbackMapper;

    public FeedbackComponentImpl(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }


    @Override
    public int insertFeedback(FeedbackReq feedbackReq, String ownerId) {
        return feedbackMapper.insertFeedback(feedbackReq, ownerId);
    }

    @Override
    public void updateFeedback(FeedbackReq feedbackReq) {
        feedbackMapper.updateFeedback(feedbackReq);
    }

    @Override
    public void deleteFeedback(int issuedVehicleId) {
        feedbackMapper.deleteFeedback(issuedVehicleId);
    }
}
