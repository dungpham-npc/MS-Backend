package com.cookswp.milkstore.service.feedback;

import com.cookswp.milkstore.pojo.dtos.FeedbackModel.FeedBackRequest;
import com.cookswp.milkstore.pojo.entities.Feedback;
import com.cookswp.milkstore.repository.feedback.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FeedBackServiceImpl implements IFeedBackService {

    private final FeedbackRepository feedbackRepository;

    public FeedBackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback addFeedback(FeedBackRequest feedBackRequest) {
        Feedback feedback = new Feedback();
        feedback.setUserID(feedBackRequest.getUserID());
        feedback.setFeedbackTime(LocalDateTime.now());
        feedback.setDescription(feedBackRequest.getDescription());
        feedback.setRating(feedBackRequest.getRating());
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(int feedbackID, FeedBackRequest request) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackID);
        if (feedback.isEmpty()) {
            throw new RuntimeException("Feedback not found");
        }
        Feedback feedbackUpdate = feedback.get();
        feedbackUpdate.setRating(request.getRating());
        feedbackUpdate.setUserID(request.getUserID());
        feedbackUpdate.setFeedbackTime(LocalDateTime.now());
        feedbackUpdate.setDescription(request.getDescription());
        return feedbackRepository.save(feedbackUpdate);
    }

    @Override
    public void deleteFeedback(int feedbackID) {
        feedbackRepository.deleteById(feedbackID);
    }
}
