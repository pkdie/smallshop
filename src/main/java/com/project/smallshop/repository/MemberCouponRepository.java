package com.project.smallshop.repository;

import com.project.smallshop.domain.MemberCoupon;

import java.util.List;

public interface MemberCouponRepository {

    void save(MemberCoupon memberCoupon);

    List<MemberCoupon> findByMemberId(Long memberId);

    MemberCoupon findByMemberCoupoonId(Long memberCouponId);
}
