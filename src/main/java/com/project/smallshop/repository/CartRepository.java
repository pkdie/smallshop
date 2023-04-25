package com.project.smallshop.repository;

import com.project.smallshop.domain.Cart;

public interface CartRepository {

    void save(Cart cart);

    Cart findByMemberId(Long memberId);
}
