package com.cookswp.milkstore.service.feedback;

import com.cookswp.milkstore.pojo.dtos.FeedbackModel.FeedBackRequest;
import com.cookswp.milkstore.pojo.entities.Feedback;

public interface IFeedBackService {

    Feedback addFeedback(FeedBackRequest feedback);

    Feedback updateFeedback(int feedbackID, FeedBackRequest feedback);

    void deleteFeedback(int feedbackID);
}
