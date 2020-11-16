package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.AuthenticationComponent;
import com.fa20se28.vma.component.FeedbackComponent;
import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;
import com.fa20se28.vma.response.FeedbackPageRes;
import com.fa20se28.vma.service.FeedbackService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackComponent feedbackComponent;
    private final AuthenticationComponent authenticationComponent;

    public FeedbackServiceImpl(FeedbackComponent feedbackComponent, AuthenticationComponent authenticationComponent) {
        this.feedbackComponent = feedbackComponent;
        this.authenticationComponent = authenticationComponent;
    }

    @Override
    public int createFeedback(FeedbackReq feedbackReq) {
        Authentication authentication = authenticationComponent.getAuthentication();
        return feedbackComponent.insertFeedback(feedbackReq,authentication.getName());
    }

    @Override
    public void updateFeedback(FeedbackReq feedbackReq) {
        feedbackComponent.updateFeedback(feedbackReq);
    }

    @Override
    public void deleteFeedback(int issuedVehicleId) {
        feedbackComponent.deleteFeedback(issuedVehicleId);
    }

    @Override
    public int getTotalFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption) {
        return feedbackComponent.getTotalFeedbacks(feedbackPageReq, viewOption);
    }

    @Override
    public FeedbackPageRes getFeedbacks(FeedbackPageReq feedbackPageReq, int viewOption, int pageNum) {
        return new FeedbackPageRes(feedbackComponent.getFeedbacks(feedbackPageReq, viewOption, pageNum));
    }
}
