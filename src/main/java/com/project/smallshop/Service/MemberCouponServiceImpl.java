package com.project.smallshop.Service;

import com.project.smallshop.domain.MemberCoupon;
import com.project.smallshop.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService{

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @Override
    public void save(MemberCoupon memberCoupon) {
        memberCouponRepository.save(memberCoupon);
    }

    @Override
    public List<MemberCoupon> findByMemberId(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId);
    }

    @Transactional
    @Override
    public Boolean checkAndUpdateCount(Long memberId, Long couponId, int count) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        for (MemberCoupon memberCoupon : memberCoupons) {
            if (memberCoupon.getCoupon().getId().equals(couponId)) {
                memberCoupon.addCount(count);

                return true;
            }
        }
        return false;
    }

    @Override
    public MemberCoupon findByMemberCouponId(Long memberCouponId) {
        return memberCouponRepository.findByMemberCoupoonId(memberCouponId);
    }
}
