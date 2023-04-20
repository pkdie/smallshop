package com.project.smallshop.Service;

import com.project.smallshop.domain.MemberCoupon;

import java.util.List;

public interface MemberCouponService {

    void save(MemberCoupon memberCoupon);

    List<MemberCoupon> findByMemberId(Long memberId);

    Boolean checkAndUpdateCount(Long memberId, Long couponId, int count);

    MemberCoupon findByMemberCouponId(Long memberCouponId);
}
