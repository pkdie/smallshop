package com.project.smallshop.Service;

import com.project.smallshop.domain.OrderItem;

public interface OrderItemService {

    OrderItem findOne(Long orderItemId);
}
