package com.project.smallshop.Service;

import com.project.smallshop.domain.*;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.domain.member.Member;
import com.project.smallshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;


    @Transactional(readOnly = false)
    @Override
    public Long order(Long memberId, String receiverName, String receiverPhone, String receiverAddress, String merchantUid) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId);
        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(receiverAddress);
        delivery.setStatus(DeliveryStatus.READY);

        //주문 생성
        Order order = Order.createOrder(member, receiverName, receiverPhone, receiverAddress, delivery, merchantUid);

        //주문상품 생성
        for (CartItem cartItem : cartItems) {

            OrderItem.createOrderItem(order, cartItem.getItem(), cartItem.getPrice(), cartItem.getCount());
            member.addSumPrice(cartItem.getPrice());
        }

        // 등급 체크 및 쿠폰 부여
        boolean sliverCheck = member.checkSliverAndUpdate();
        if (sliverCheck) {
            Coupon sliverCoupon = couponRepository.findById(3L);
            MemberCoupon sliverMemberCoupon = MemberCoupon.createMemberCoupon(member, sliverCoupon, 1);
            memberCouponRepository.save(sliverMemberCoupon);
        }
        boolean goldCheck = member.checkGoldAndUpdate();
        if (goldCheck) {
            Coupon goldCoupon = couponRepository.findById(4L);
            MemberCoupon goldMemberCoupon = MemberCoupon.createMemberCoupon(member, goldCoupon, 1);
            memberCouponRepository.save(goldMemberCoupon);
        }

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional(readOnly = false)
    @Override
    public Long couponOrder(Long memberId, String receiverName, String receiverPhone, String receiverAddress, Long memberCouponId, String merchantUid) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId);
        List<CartItem> cartItems = cartItemRepository.findByMemberId(memberId);
        MemberCoupon memberCoupon = memberCouponRepository.findByMemberCoupoonId(memberCouponId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(receiverAddress);
        delivery.setStatus(DeliveryStatus.READY);

        //주문 생성
        Order order = Order.createOrder(member, receiverName, receiverPhone, receiverAddress, delivery, merchantUid);

        //주문상품 생성
        for (CartItem cartItem : cartItems) {

            if (memberCoupon.getCoupon().getType().name().equals(cartItem.getItem().getDtype().name()) || memberCoupon.getCoupon().getType().name().equals("ALL")) {

                int orderPrice = cartItem.getPrice() * (100 - memberCoupon.getCoupon().getDiscount()) / 100;
                OrderItem.createOrderItem(order, cartItem.getItem(), orderPrice, cartItem.getCount());
                member.addSumPrice(orderPrice);
                continue;
            }

            OrderItem.createOrderItem(order, cartItem.getItem(), cartItem.getPrice(), cartItem.getCount());
        }

        // 등급 체크 및 쿠폰 부여
        boolean sliverCheck = member.checkSliverAndUpdate();
        if (sliverCheck) {
            Coupon sliverCoupon = couponRepository.findById(3L);
            MemberCoupon sliverMemberCoupon = MemberCoupon.createMemberCoupon(member, sliverCoupon, 1);
            memberCouponRepository.save(sliverMemberCoupon);
        }
        boolean goldCheck = member.checkGoldAndUpdate();
        if (goldCheck) {
            Coupon goldCoupon = couponRepository.findById(4L);
            MemberCoupon goldMemberCoupon = MemberCoupon.createMemberCoupon(member, goldCoupon, 1);
            memberCouponRepository.save(goldMemberCoupon);
        }

        //쿠폰 하나 제거
        memberCoupon.removeOne();

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional(readOnly = false)
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancelOrder();
    }

    @Override
    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    public List<Order> findByMemberId(Long memberId) {
        return orderRepository.findAllByMemberId(memberId);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        if (order.getStatus() == OrderStatus.CANCEL) {
            throw new IllegalStateException("주문취소 된 상품을 주문완료 할 수 없습니다.");
        }
        if (order.getStatus() == OrderStatus.COMP) {
            throw new IllegalStateException("이미 주문완료 되었습니다.");
        }
        order.setStatus(OrderStatus.COMP);
        order.getDelivery().setStatus(DeliveryStatus.COMP);
    }
}
