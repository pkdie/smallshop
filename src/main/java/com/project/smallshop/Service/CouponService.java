package com.project.smallshop.Service;

import com.project.smallshop.domain.Coupon;

import java.util.List;

public interface CouponService {

    void save(Coupon coupon);

    Coupon findById(Long couponId);

    List<Coupon> findAll();
}
