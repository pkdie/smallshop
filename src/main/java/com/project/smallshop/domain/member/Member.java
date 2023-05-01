package com.project.smallshop.domain.member;

import com.project.smallshop.domain.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String pwd;

    private String name;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    private int sumPrice;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    // OneToOne관계에선 단방향으로만 만들어야 n + 1문제가 해결된다.
//    @OneToOne(mappedBy = "member", fetch = LAZY)
//    private Cart cart;

    // ---------------- 비즈니스 로직 ----------

    public static Member createMember(String email, String pwd, String name, String phone, String address) {
        Member member = new Member();
        member.setEmail(email);
        member.setPwd(pwd);
        member.setName(name);
        member.setPhone(phone);
        member.setAddress(address);
        member.setType(MemberType.USER);
        member.setDate(LocalDateTime.now());
        member.setGrade(MemberGrade.BRONZE);
        member.setSumPrice(0);

        return member;
    }

    public void addSumPrice(int price) {
        this.sumPrice += price;
    }

    public boolean checkSliverAndUpdate() {
        if (this.getGrade().name().equals("BRONZE") && this.sumPrice >= 50000) {
            this.setGrade(MemberGrade.SLIVER);

            return true;
        }
        return false;
    }

    public boolean checkGoldAndUpdate() {
        if (this.getGrade().name().equals("SLIVER") && this.sumPrice >= 100000) {
            this.setGrade(MemberGrade.GOLD);

            return true;
        }
        return false;
    }
}
