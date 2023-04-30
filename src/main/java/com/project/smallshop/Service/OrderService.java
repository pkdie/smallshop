package com.project.smallshop.Service;

import com.project.smallshop.domain.Order;

import java.util.List;

public interface OrderService {

    Long order(Long memberId, String receiverName, String receiverPhone, String receiverAddress, String merchantUid);

    Long couponOrder(Long memberId, String receiverName, String receiverPhone, String receiverAddress, Long memberCouponId, String merchantUid);

    void cancelOrder(Long orderId);

    Order findOne(Long orderId);

    List<Order> findByMemberId (Long memberId);

    void updateOrder(Long orderId);
}
