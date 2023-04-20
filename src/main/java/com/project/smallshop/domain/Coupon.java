package com.project.smallshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private String name;

    private int discount;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    // ---------------------------------- 비즈니스 로직 -------------------

    public static Coupon createCoupon(String name, int discount, CouponType type) {
        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setDiscount(discount);
        coupon.setType(type);

        return coupon;
    }
}
