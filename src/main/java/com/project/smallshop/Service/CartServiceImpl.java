package com.project.smallshop.Service;

import com.project.smallshop.domain.Cart;
import com.project.smallshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;


    @Override
    @Transactional(readOnly = false)
    public Long save(Cart cart) {
        cartRepository.save(cart);
        return cart.getId();
    }

    @Override
    public Cart findByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }
}
