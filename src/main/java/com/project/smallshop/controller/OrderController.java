package com.project.smallshop.controller;

import com.project.smallshop.Service.*;
import com.project.smallshop.domain.*;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CartItemService cartItemService;
    private final MemberCouponService memberCouponService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ItemService itemService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final OrderItemService orderItemService;

    @GetMapping("/order")
    public String order(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        List<CartItem> cartItems = cartItemService.findByMemberId(member.getId());
        if (cartItems.isEmpty()) {
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("msg", "장바구니가 비어있습니다.");
            return "cart";
        }
        List<MemberCoupon> memberCoupons = memberCouponService.findByMemberId(member.getId());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("memberCoupons", memberCoupons);
        model.addAttribute("orderForm", new OrderForm());

        return "order/order";
    }

    @PostMapping("/order")
    public String createOrder(@Valid OrderForm form, BindingResult result, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");

        if (result.hasErrors()) {

            List<CartItem> cartItems = cartItemService.findByMemberId(member.getId());
            List<MemberCoupon> memberCoupons = memberCouponService.findByMemberId(member.getId());
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("memberCoupons", memberCoupons);

            return "order/order";
        }

        Cart cart = cartService.findByMemberId(member.getId());
        orderService.order(member.getId(), form.getReceiverName(), form.getReceiverPhone(), form.getReceiverAddress());
        cartItemService.removeMemberCart(cart.getId());

        return "redirect:/order/complete";
    }

    @GetMapping("/order/coupon/{memberCouponId}")
    public String couponOrder(@PathVariable("memberCouponId") Long memberCouponId,
                              Model model,
                              HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        List<CartItem> cartItems = cartItemService.findByMemberId(member.getId());
        List<MemberCoupon> memberCoupons = memberCouponService.findByMemberId(member.getId());
        MemberCoupon findMemberCoupon = memberCouponService.findByMemberCouponId(memberCouponId);
        model.addAttribute("findMemberCoupon", findMemberCoupon);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("memberCoupons", memberCoupons);
        model.addAttribute("orderForm", new OrderForm());

        return "order/couponOrder";
    }

    @PostMapping("/order/coupon/{memberCouponId}")
    public String createCouponOrder(@PathVariable("memberCouponId") Long memberCouponId,
                                    @Valid OrderForm form,
                                    BindingResult result,
                                    Model model,
                                    HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");

        if (result.hasErrors()) {

            List<CartItem> cartItems = cartItemService.findByMemberId(member.getId());
            List<MemberCoupon> memberCoupons = memberCouponService.findByMemberId(member.getId());
            MemberCoupon findMemberCoupon = memberCouponService.findByMemberCouponId(memberCouponId);
            model.addAttribute("findMemberCoupon", findMemberCoupon);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("memberCoupons", memberCoupons);

            return "order/couponOrder";
        }

        Cart cart = cartService.findByMemberId(member.getId());
        orderService.couponOrder(member.getId(), form.getReceiverName(), form.getReceiverPhone(), form.getReceiverAddress(), memberCouponId);
        cartItemService.removeMemberCart(cart.getId());

        return "redirect:/order/complete";
    }

    @GetMapping("/orderList")
    public String orderList(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");

        List<Order> orderList = orderService.findByMemberId(member.getId());
        model.addAttribute("orderList", orderList);

        return "order/orderList";
    }

    @GetMapping("/orderList/view/{orderId}")
    public String orderDetails(@PathVariable("orderId") Long orderId, Model model) {

        Order order = orderService.findOne(orderId);
        model.addAttribute("order", order);

        return "order/orderDetails";
    }

    @GetMapping("/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {

        try {
            orderService.cancelOrder(orderId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());

            return "redirect:/orderList/view/{orderId}";
        }

        return "redirect:/orderList/view/{orderId}";
    }

    @GetMapping("/order/complete")
    public String orderComplete() {
        return "order/orderCompletePage";
    }

    @GetMapping("/order/success/{orderId}")
    public String orderSuccess(@PathVariable("orderId") Long orderId, RedirectAttributes redirectAttributes) {

        try {
            orderService.updateOrder(orderId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/orderList/view/{orderId}";
    }
}
