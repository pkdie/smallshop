package com.project.smallshop.Service;

import com.project.smallshop.domain.Review;
import org.springframework.transaction.annotation.Transactional;


public interface ReviewService {

    void save(Review review);

    Review findByOrderItemId(Long orderItemId);

    void updateReview(Long orderItemId, String title, String content);

    void removeReviewByOrderItemId(Long orderItemId);
}
