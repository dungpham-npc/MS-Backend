package com.cookswp.milkstore.service.feedback;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.FeedbackModel.FeedBackRequest;
import com.cookswp.milkstore.pojo.entities.Feedback;
import com.cookswp.milkstore.repository.feedback.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        int rating = feedBackRequest.getRating();
        if(rating < 1 || rating > 5 ){
            throw new AppException(ErrorCode.FEEDBACK_RATING_ERROR);
        }
        feedback.setRating(rating);
        feedback.setUserID(feedBackRequest.getUserID());
        feedback.setFeedbackTime(LocalDateTime.now());
        feedback.setDescription(feedBackRequest.getDescription());
        feedback.setStatus(true);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback updateFeedback(int feedbackID, FeedBackRequest request) {
        Feedback feedback = feedbackRepository.findByFeedbackIDAndAndStatus(feedbackID);
        if (feedback == null) {
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        int rating = request.getRating();
        if(rating < 1 || rating > 5 ){
            throw new AppException(ErrorCode.FEEDBACK_RATING_ERROR);
        }
        feedback.setRating(rating);
        feedback.setUserID(request.getUserID());
        feedback.setDescription(request.getDescription());
        feedback.setFeedbackTime(LocalDateTime.now());
        feedback.setStatus(true);
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(int feedbackID) {
        Feedback feedback= feedbackRepository.findByFeedbackIDAndAndStatus(feedbackID);
        if (feedback == null) {
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        feedback.setStatus(false);
        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.getAllFeedback();
    }

    @Override
    public Feedback getFeedbackByID(int feedbackID) {
        Feedback feedback =  feedbackRepository.findByFeedbackID(feedbackID);
        if (feedback == null) {
            throw new AppException(ErrorCode.FEEDBACK_NOT_FOUND);
        }
        return feedback;
    }
}
