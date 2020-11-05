package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.FeedbackReq;
import com.fa20se28.vma.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int createFeedback(@RequestBody FeedbackReq feedbackReq) {
        return feedbackService.createFeedback(feedbackReq);
    }

    @PutMapping
    public void updateFeedback(@RequestBody FeedbackReq feedbackReq) {
        feedbackService.updateFeedback(feedbackReq);
    }

    @DeleteMapping("/{feedback-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedback(@PathVariable int issuedVehicleId) {
        feedbackService.deleteFeedback(issuedVehicleId);
    }
}
