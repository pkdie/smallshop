package com.project.smallshop.repository;

import com.project.smallshop.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final EntityManager em;

    @Override
    public void save(Cart cart) {
        em.persist(cart);
    }

    @Override
    public Cart findByMemberId(Long memberId) {
        return em.createQuery("select c from Cart c where c.member.id = :memberId", Cart.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
