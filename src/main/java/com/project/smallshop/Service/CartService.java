package com.project.smallshop.Service;

import com.project.smallshop.domain.Cart;

public interface CartService {

    Long save(Cart cart);

    Cart findByMemberId(Long memberId);
}
