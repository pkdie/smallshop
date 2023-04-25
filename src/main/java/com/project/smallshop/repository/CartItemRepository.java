package com.project.smallshop.repository;

import com.project.smallshop.domain.CartItem;

import java.util.List;

public interface CartItemRepository {

    void save(CartItem cartItem);

    List<CartItem> findByCartId(Long cartId);

    List<CartItem> findByMemberId(Long memberId);

    void remove(Long cartItemId);

    void removeByCartId(Long cartId);

    void update(Long cartItemId, int count);
}
