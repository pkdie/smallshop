package com.project.smallshop.repository;

import com.project.smallshop.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberCouponRepositoryImpl implements MemberCouponRepository{

    private final EntityManager em;

    @Override
    public void save(MemberCoupon memberCoupon) {
        em.persist(memberCoupon);
    }

    @Override
    public List<MemberCoupon> findByMemberId(Long memberId) {
        return em.createQuery("select m from MemberCoupon m where m.member.id = :memberId", MemberCoupon.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public MemberCoupon findByMemberCoupoonId(Long memberCouponId) {
        return em.find(MemberCoupon.class, memberCouponId);
    }
}
