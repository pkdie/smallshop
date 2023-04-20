package com.project.smallshop.domain;

import com.project.smallshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int price;

    private int count;

    // -------------------------------- 비즈니스 로직

    public int addCount(int count) {
        this.count += count;
        return this.count;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.getCartItems().add(this);
    }

    public void setItem(Item item) {
        this.item = item;
        item.getCartItems().add(this);
    }

    public static CartItem createCartItem(Cart cart, Item item, int price, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setPrice(price);
        cartItem.setCount(count);

        return cartItem;
    }
}
