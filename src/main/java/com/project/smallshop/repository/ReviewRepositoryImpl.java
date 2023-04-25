package com.project.smallshop.repository;

import com.project.smallshop.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository{

    private final EntityManager em;


    @Override
    public void save(Review review) {
        em.persist(review);
    }

    @Override
    public Review findByOrderItemId(Long orderItemId) {
        return em.createQuery("select r from Review r where orderItem.id = :orderItemId", Review.class)
                .setParameter("orderItemId", orderItemId)
                .getSingleResult();
    }

    @Override
    public void remove(Review review) {
        em.remove(review);
    }
}
