package com.project.smallshop.Service;

import com.project.smallshop.domain.Coupon;
import com.project.smallshop.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;

    @Transactional(readOnly = false)
    @Override
    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Override
    public Coupon findById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }
}
