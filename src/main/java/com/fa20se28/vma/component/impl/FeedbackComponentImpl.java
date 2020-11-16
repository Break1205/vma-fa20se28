package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.FeedbackComponent;
import com.fa20se28.vma.mapper.FeedbackMapper;
import com.fa20se28.vma.model.Feedback;
import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public int getTotalFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption) {
        return feedbackMapper.getFeedbackCount(feedbackPageReq, viewOption);
    }

    @Override
    public List<Feedback> getFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption, int pageNum) {
        return feedbackMapper.getFeedbacks(feedbackPageReq, viewOption, pageNum*15);
    }
}
