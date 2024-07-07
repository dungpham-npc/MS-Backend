package com.cookswp.milkstore.repository.feedback;

import com.cookswp.milkstore.pojo.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
