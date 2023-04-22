package com.project.smallshop.controller;

import com.project.smallshop.Service.CartItemService;
import com.project.smallshop.domain.CartItem;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartItemService cartItemService;

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession(false);
        if (session == null) {

            redirectAttributes.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");

            return "redirect:/";
        }
        Member member = (Member) session.getAttribute("member");
        if (member == null) {

            redirectAttributes.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");

            return "redirect:/";
        }
        List<CartItem> cartItems = cartItemService.findByMemberId(member.getId());
        model.addAttribute("cartItems", cartItems);

        return "cart";
    }

    @GetMapping("/cart/{cartItemId}/remove")
    public String cartRemove(@PathVariable("cartItemId") Long cartItemId) {

        cartItemService.remove(cartItemId);

        return "redirect:/cart";
    }

    @GetMapping("/cart/{cartItemId}/update")
    public String update(@PathVariable("cartItemId") Long cartItemId,
                         @RequestParam("count") int count) {

        cartItemService.update(cartItemId, count);

        return "redirect:/cart";
    }
}
