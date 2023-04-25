package com.project.smallshop.domain;

import com.project.smallshop.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class MemberCoupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private int count;

    private LocalDate startDate;

    private LocalDate endDate;

    // ---------------------- 비즈니스 로직

    public void setMember(Member member) {
        this.member = member;
        member.getMemberCoupons().add(this);
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
        coupon.getMemberCoupons().add(this);
    }

    public static MemberCoupon createMemberCoupon(Member member, Coupon coupon, int count) {
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setMember(member);
        memberCoupon.setCoupon(coupon);
        memberCoupon.setCount(count);
        memberCoupon.setStartDate(LocalDate.now());
        memberCoupon.setEndDate(LocalDate.now().plusMonths(1));

        return memberCoupon;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void removeOne() {
        this.count -= 1;
    }
}
