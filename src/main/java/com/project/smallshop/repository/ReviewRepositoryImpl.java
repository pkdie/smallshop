package com.project.smallshop.repository;

import com.project.smallshop.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository{

    private final EntityManager em;


    @Override
    public void save(Review review) {
        em.persist(review);
    }
}
