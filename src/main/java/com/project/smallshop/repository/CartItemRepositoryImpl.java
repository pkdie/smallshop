package com.project.smallshop.repository;

import com.project.smallshop.domain.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final EntityManager em;

    @Override
    public void save(CartItem cartItem) {
        em.persist(cartItem);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return em.createQuery("select c from CartItem c where c.cart.id = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public List<CartItem> findByMemberId(Long memberId) {
        return em.createQuery("select c from CartItem c where c.cart.member.id = :memberId", CartItem.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void remove(Long cartItemId) {
        CartItem cartItem = em.find(CartItem.class, cartItemId);
        em.remove(cartItem);
    }

    @Override
    public void removeByCartId(Long cartId) {
        em.createQuery("delete from CartItem c where c.cart.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();
    }

    @Override
    public void update(Long cartItemId, int count) {
        CartItem cartItem = em.find(CartItem.class, cartItemId);
        int itemPrice = cartItem.getPrice() / cartItem.getCount();
        cartItem.setCount(count);
        cartItem.setPrice(itemPrice * count);
    }
}
