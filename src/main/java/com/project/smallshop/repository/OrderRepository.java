package com.project.smallshop.repository;

import com.project.smallshop.domain.Order;

import java.util.List;

public interface OrderRepository {

    void save(Order order);

    Order findOne(Long orderId);

    List<Order> findAllByMemberId(Long memberId);
}
