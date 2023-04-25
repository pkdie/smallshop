package com.project.smallshop.Service;

import com.project.smallshop.domain.Coupon;
import com.project.smallshop.domain.MemberCoupon;
import com.project.smallshop.domain.member.Member;
import com.project.smallshop.domain.member.MemberType;
import com.project.smallshop.repository.CouponRepository;
import com.project.smallshop.repository.MemberCouponRepository;
import com.project.smallshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    @Transactional(readOnly = false)
    public Long join(Member member) {
        List<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (!findMember.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        memberRepository.save(member);
        Coupon coupon = couponRepository.findById(1L);
        MemberCoupon memberCoupon = MemberCoupon.createMemberCoupon(member, coupon, 1);
        memberCouponRepository.save(memberCoupon);

        return member.getId();
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member login(String email, String pwd) {
        List<Member> memberList = memberRepository.findByEmail(email);
        if (memberList.size() == 0) {
            throw new IllegalStateException("이메일과 비밀번호를 확인하십시오");
        }

        Member member = memberList.get(0);
        if (!member.getPwd().equals(pwd)) {
            throw new IllegalStateException("이메일과 비밀번호를 확인하십시오");
        }

        return member;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    @Override
    public void updateTypeToAdmin(Long memberId) {
        Member member = memberRepository.findById(memberId);
        member.setType(MemberType.ADMIN);
    }

    @Transactional
    @Override
    public void updateTypeToUser(Long memberId) {
        Member member = memberRepository.findById(memberId);
        member.setType(MemberType.USER);
    }
}
