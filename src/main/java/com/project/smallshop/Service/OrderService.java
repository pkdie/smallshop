package com.project.smallshop.Service;

import com.project.smallshop.domain.Order;
import com.project.smallshop.domain.member.Member;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Long order(Long memberId, String receiverName, String receiverPhone, String receiverAddress);

    Long couponOrder(Long memberId, String receiverName, String receiverPhone, String receiverAddress, Long memberCouponId);

    void cancelOrder(Long orderId);

    Order findOne(Long orderId);

    List<Order> findByMemberId (Long memberId);

    void updateOrder(Long orderId);
}
