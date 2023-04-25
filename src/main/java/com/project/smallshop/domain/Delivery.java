package com.project.smallshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    // ---------------- 비즈니스 로직 --------------
    public void setOrder(Order order) {
        this.order = order;
        order.setDelivery(this);
    }

    public void cancel() {
        this.setStatus(DeliveryStatus.CANCEL);
    }
}
