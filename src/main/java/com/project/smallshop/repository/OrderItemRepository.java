package com.project.smallshop.repository;

import com.project.smallshop.domain.OrderItem;

public interface OrderItemRepository {

    OrderItem findOne(Long orderItemId);
}
