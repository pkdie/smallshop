package com.project.smallshop.Service;

import com.project.smallshop.domain.CartItem;

import java.util.List;

public interface CartItemService {

    void save(CartItem cartItem);

    List<CartItem> findByMemberId(Long memberId);

    Boolean checkAndUpdateCart(Long cartId, Long itemId, int count);

    void remove(Long cartItemId);

    void removeMemberCart(Long cartId);

    void update(Long cartItemId, int count);
}
