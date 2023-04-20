package com.project.smallshop.repository;

import com.project.smallshop.domain.Coupon;

import java.util.List;

public interface CouponRepository {

    void save(Coupon coupon);

    Coupon findById(Long couponId);

    List<Coupon> findAll();
}
