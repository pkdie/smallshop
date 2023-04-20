package com.project.smallshop.repository;

import com.project.smallshop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager em;

    @Override
    public void save(Order order) {
        em.persist(order);
    }

    @Override
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public List<Order> findAllByMemberId(Long memberId) {
        return em.createQuery("select o from Order o where o.member.id = :memberId", Order.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
