package com.project.smallshop.domain;

import com.project.smallshop.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", fetch = LAZY, cascade = CascadeType.ALL)
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // ----------------------- 비즈니스 로직 ----------------------

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public static Order createOrder(Member member, String receiverName, String receiverPhone, String receiverAddress, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddress(receiverAddress);
        delivery.setOrder(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);

        return order;
    }

    public void cancelOrder() {

        if (this.delivery.getStatus() == DeliveryStatus.SHIPPING || this.delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("배송 중이거나 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        delivery.cancel();
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
