package com.fa20se28.vma.controller;

import com.fa20se28.vma.request.FeedbackPageReq;
import com.fa20se28.vma.request.FeedbackReq;
import com.fa20se28.vma.response.FeedbackPageRes;
import com.fa20se28.vma.service.FeedbackService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public void deleteFeedback(@PathVariable("feedback-id") int feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }

    @GetMapping("/count")
    public int getTotalFeedbacks(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String contributorId,
            @RequestParam(required = false) String contributorName,
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) String driverName,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDate,
            @RequestParam(required = false, defaultValue = "0") int rateMin,
            @RequestParam(required = false, defaultValue = "0") int rateMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption) {
        return feedbackService.getTotalFeedbacks(
                new FeedbackPageReq(
                        vehicleId,
                        contributorId, contributorName,
                        driverId, driverName,
                        fromDate, toDate,
                        rateMin, rateMax),
                viewOption);
    }

    @GetMapping
    public FeedbackPageRes getFeedbacks(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String contributorId,
            @RequestParam(required = false) String contributorName,
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) String driverName,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime toDate,
            @RequestParam(required = false, defaultValue = "0") int rateMin,
            @RequestParam(required = false, defaultValue = "0") int rateMax,
            @RequestParam(required = false, defaultValue = "0") int viewOption,
            @RequestParam(required = false, defaultValue = "0") int pageNum) {
        return feedbackService.getFeedbacks(
                new FeedbackPageReq(
                        vehicleId,
                        contributorId, contributorName,
                        driverId, driverName,
                        fromDate, toDate,
                        rateMin, rateMax),
                viewOption,
                pageNum);
    }
}
