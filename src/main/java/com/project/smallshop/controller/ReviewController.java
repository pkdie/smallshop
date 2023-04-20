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

        return "item/createOrderItemReview";
    }

    @PostMapping("/item/{orderItemId}/review")
    public String createOrderReview(@Valid reviewForm form, BindingResult result, HttpServletRequest request, @PathVariable("orderItemId") Long orderItemId, Model model) {

        OrderItem orderItem = orderItemService.findOne(orderItemId);
        if (result.hasErrors()) {

            model.addAttribute("item", orderItem.getItem());
            return "item/createOrderItemReview";
        }

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute("member");
        Member findMember = memberService.findById(member.getId());

        Review review = Review.createReview(findMember, orderItem.getItem(), form.getTitle(), form.getContent());
        orderItem.setReviewCheck(true);
        reviewService.save(review);

        return "redirect:/";
    }
}
