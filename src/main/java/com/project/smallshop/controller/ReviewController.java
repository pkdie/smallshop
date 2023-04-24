package com.project.smallshop.controller;

import com.project.smallshop.Service.MemberService;
import com.project.smallshop.Service.OrderItemService;
import com.project.smallshop.Service.ReviewService;
import com.project.smallshop.domain.OrderItem;
import com.project.smallshop.domain.Review;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final OrderItemService orderItemService;
    private final MemberService memberService;
    private final ReviewService reviewService;

    @GetMapping("/item/{orderItemId}/review")
    public String createReviewOrder(@PathVariable("orderItemId") Long orderItemId, Model model) {

        OrderItem orderItem = orderItemService.findOne(orderItemId);
        model.addAttribute("item", orderItem.getItem());
        model.addAttribute("reviewForm", new reviewForm());

        return "review/createOrderItemReview";
    }

    @PostMapping("/item/{orderItemId}/review")
    public String createOrderReview(@Valid reviewForm form, BindingResult result, HttpServletRequest request, @PathVariable("orderItemId") Long orderItemId, Model model) {

        OrderItem orderItem = orderItemService.findOne(orderItemId);
        if (result.hasErrors()) {

            model.addAttribute("item", orderItem.getItem());
            return "review/createOrderItemReview";
        }

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        Member findMember = memberService.findById(member.getId());

        Review review = Review.createReview(findMember, orderItem.getItem(), orderItem, form.getTitle(), form.getContent());
        orderItem.setReviewCheck(true);
        reviewService.save(review);

        return "redirect:/";
    }

    @GetMapping("/item/{orderItemId}/review/update")
    public String updateReview(@PathVariable("orderItemId") Long orderItemId, Model model) {

        OrderItem orderItem = orderItemService.findOne(orderItemId);
        Review review = reviewService.findByOrderItemId(orderItemId);

        reviewForm reviewForm = new reviewForm();
        reviewForm.setTitle(review.getTitle());
        reviewForm.setContent(review.getContent());

        model.addAttribute("item", orderItem.getItem());
        model.addAttribute("reviewForm", reviewForm);

        return "review/updateOrderItemReview";
    }

    @PostMapping("/item/{orderItemId}/review/update")
    public String updateItemReview(@Valid reviewForm form, BindingResult result,
                                   @PathVariable("orderItemId") Long orderItemId, Model model) {

        OrderItem orderItem = orderItemService.findOne(orderItemId);

        if (result.hasErrors()) {

            model.addAttribute("item", orderItem.getItem());

            return "review/updateOrderItemReview";
        }

        reviewService.updateReview(orderItemId, form.getTitle(), form.getContent());

        return "redirect:/orderList/view/" + orderItem.getOrder().getId();
    }

    @GetMapping("/item/{orderItemId}/review/remove")
    public String removeReview(@PathVariable("orderItemId") Long orderItemId) {

        Long orderId = orderItemService.findOne(orderItemId).getOrder().getId();
        reviewService.removeReviewByOrderItemId(orderItemId);

        return "redirect:/orderList/view/" + orderId;
    }
}
