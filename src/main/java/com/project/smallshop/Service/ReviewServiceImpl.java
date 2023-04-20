package com.project.smallshop.Service;

import com.project.smallshop.domain.Review;
import com.project.smallshop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = false)
    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }
}
