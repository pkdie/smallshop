package com.project.smallshop.repository;

import com.project.smallshop.domain.Review;


public interface ReviewRepository {

    void save(Review review);

    Review findByOrderItemId(Long orderItemId);

    void remove(Review review);
}
