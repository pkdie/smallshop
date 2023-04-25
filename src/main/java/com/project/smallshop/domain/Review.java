package com.project.smallshop.domain;

import com.project.smallshop.domain.item.Item;
import com.project.smallshop.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private LocalDateTime date;

    private String title;

    private String content;

    // ----------------------- 비즈니스 로직 -----------------
    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getReviews().add(this);
    }

    public static Review createReview(Member member, Item item, OrderItem orderItem, String title, String content) {
        Review review = new Review();
        review.setMember(member);
        review.setItem(item);
        review.setOrderItem(orderItem);
        review.setTitle(title);
        review.setContent(content);
        review.setDate(LocalDateTime.now());

        return review;
    }
}
