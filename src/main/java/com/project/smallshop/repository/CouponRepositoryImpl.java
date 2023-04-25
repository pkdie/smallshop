package com.project.smallshop.repository;

import com.project.smallshop.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository{

    private final EntityManager em;

    @Override
    public void save(Coupon coupon) {
        em.persist(coupon);
    }

    @Override
    public Coupon findById(Long couponId) {
        return em.find(Coupon.class, couponId);
    }

    @Override
    public List<Coupon> findAll() {
        return em.createQuery("select c from Coupon c", Coupon.class)
                .getResultList();
    }
}
