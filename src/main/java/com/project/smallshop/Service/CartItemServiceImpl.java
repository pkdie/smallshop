package com.project.smallshop.Service;

import com.project.smallshop.domain.CartItem;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.repository.CartItemRepository;
import com.project.smallshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;


    @Transactional
    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByMemberId(Long memberId) {
        return cartItemRepository.findByMemberId(memberId);
    }

    @Transactional
    @Override
    public Boolean checkAndUpdateCart(Long cartId, Long itemId, int count) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().getId().equals(itemId)) {
                int count1 = cartItem.addCount(count);
                Item item = itemRepository.findOne(itemId);
                int price = item.totalPrice(count1);
                cartItem.setPrice(price);

                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void remove(Long cartItemId) {
        cartItemRepository.remove(cartItemId);
    }

    @Transactional(readOnly = false)
    @Override
    public void removeMemberCart(Long cartId) {
        cartItemRepository.removeByCartId(cartId);
    }

    @Transactional
    @Override
    public void update(Long cartItemId, int count) {
        cartItemRepository.update(cartItemId, count);
    }
}
