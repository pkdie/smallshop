package com.project.smallshop.repository;

import com.project.smallshop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository{

    private final EntityManager em;

    @Override
    public OrderItem findOne(Long orderItemId) {
        return em.find(OrderItem.class, orderItemId);
    }
}
