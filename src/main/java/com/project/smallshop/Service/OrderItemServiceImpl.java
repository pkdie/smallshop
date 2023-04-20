package com.project.smallshop.Service;

import com.project.smallshop.domain.OrderItem;
import com.project.smallshop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem findOne(Long orderItemId) {
        return orderItemRepository.findOne(orderItemId);
    }
}
