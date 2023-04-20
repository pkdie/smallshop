package com.project.smallshop.domain;

import com.project.smallshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

    private boolean reviewCheck;

    // --------------------------- 비즈니스 로직 --------------------

    public void setOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItems().add(this);
    }

    public static OrderItem createOrderItem(Order order, Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        orderItem.setReviewCheck(false);

        item.removeStock(count);

        return orderItem;
    }

    public void cancel() {
        getItem().addStock(count);
    }
}
