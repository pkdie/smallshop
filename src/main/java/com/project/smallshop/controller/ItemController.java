package com.project.smallshop.controller;

import com.project.smallshop.Service.CartItemService;
import com.project.smallshop.Service.CartService;
import com.project.smallshop.Service.ItemService;
import com.project.smallshop.domain.Cart;
import com.project.smallshop.domain.CartItem;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        List<Item> otherItems = itemService.findOtherItems(itemId);
        model.addAttribute("item", item);
        model.addAttribute("otherItems", otherItems);

        return "item/shopItem";
    }

    @PostMapping("/item/{itemId}")
    public String addToCart(@RequestParam("inputQuantity") int count,
                          @PathVariable("itemId") Long itemId,
                          HttpServletRequest request,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {

            redirectAttributes.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");

            return "redirect:/item/{itemId}";
        }
        Member member = (Member) session.getAttribute("member");
        if (member == null) {

            redirectAttributes.addFlashAttribute("msg", "로그인이 필요한 서비스입니다.");

            return "redirect:/item/{itemId}";
        }
        Cart cart = cartService.findByMemberId(member.getId());
        Boolean check = cartItemService.checkAndUpdateCart(cart.getId(), itemId, count);
        if (check) {

            redirectAttributes.addFlashAttribute("msg", "장바구니에 추가되었습니다.");

            return "redirect:/item/{itemId}";
        }
        Item item = itemService.findOne(itemId);
        int price = item.totalPrice(count);
        CartItem cartItem = CartItem.createCartItem(cart, item, price, count);
        cartItemService.save(cartItem);
        model.addAttribute("item", item);
        redirectAttributes.addFlashAttribute("msg", "장바구니에 추가되었습니다.");

        return "redirect:/item/{itemId}";
    }
}
