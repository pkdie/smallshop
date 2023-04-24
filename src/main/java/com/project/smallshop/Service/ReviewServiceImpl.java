package com.project.smallshop.Service;

import com.project.smallshop.domain.OrderItem;
import com.project.smallshop.domain.Review;
import com.project.smallshop.repository.OrderItemRepository;
import com.project.smallshop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = false)
    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Review findByOrderItemId(Long orderItemId) {
        return reviewRepository.findByOrderItemId(orderItemId);
    }

    @Transactional
    @Override
    public void updateReview(Long orderItemId, String title, String content) {
        Review review = reviewRepository.findByOrderItemId(orderItemId);
        review.setTitle(title);
        review.setContent(content);
    }

    @Transactional(readOnly = false)
    @Override
    public void removeReviewByOrderItemId(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findOne(orderItemId);
        orderItem.setReviewCheck(false);
        Review review = reviewRepository.findByOrderItemId(orderItemId);
        reviewRepository.remove(review);
    }
}
