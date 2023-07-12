package com.project.smallshop.controller;

import com.project.smallshop.Service.*;
import com.project.smallshop.domain.Coupon;
import com.project.smallshop.domain.MemberCoupon;
import com.project.smallshop.domain.item.Item;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final CouponService couponService;
    private final MemberCouponService memberCouponService;
    private final S3Uploader s3Uploader;

    @GetMapping("admin/home")
    public String admin() {
        return "admin/home";
    }

    @GetMapping("admin/item/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "admin/createItemForm";
    }

    @PostMapping(value = "admin/item/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@RequestParam("image") MultipartFile imageFile,
                         @RequestParam("content") MultipartFile contentFile,
                         ItemForm form) throws IOException {

        String imageUrl = s3Uploader.upload(imageFile, "images");
        String contentUrl = s3Uploader.upload(contentFile, "images");

        Item item = Item.createItem(form.getDtype(), form.getName(), form.getPrice(), form.getStock(), imageUrl, contentUrl, form.getOutline() ,form.getAuthor(), form.getIsbn(), form.getBrand());

        itemService.save(item);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "admin/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        ItemForm form = new ItemForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStock(item.getStock());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setBrand(item.getBrand());

        model.addAttribute("form", form);

        return "admin/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStock(), form.getAuthor(), form.getIsbn(), form.getBrand());

        return "redirect:/admin/items";
    }

    @GetMapping("/items/{itemId}/remove")
    public String remove(@PathVariable("itemId") Long itemId) {
        itemService.remove(itemId);

        return "redirect:/admin/items";
    }

    @GetMapping("/admin/coupon/new")
    public String createCouponForm(Model model) {
        model.addAttribute("couponForm", new CouponForm());
        return "admin/createCouponForm";
    }

    @PostMapping("/admin/coupon/new")
    public String createCoupon(@Valid CouponForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "admin/createCouponForm";
        }

        Coupon coupon = Coupon.createCoupon(form.getName(), form.getDiscount(), form.getType());
        couponService.save(coupon);

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/members")
    public String members(Model model) {

        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);

        return "admin/memberList";
    }

    @GetMapping("/admin/{memberId}/edit")
    public String updateType(@PathVariable("memberId") Long memberId) {

        memberService.updateTypeToAdmin(memberId);

        return "redirect:/admin/members";
    }

    @GetMapping("/admin/{memberId}/remove")
    public String updateUser(@PathVariable("memberId") Long memberId) {

        memberService.updateTypeToUser(memberId);

        return "redirect:/admin/members";
    }

    @GetMapping("/admin/coupons")
    public String memberCoupon(Model model) {

        List<Member> members = memberService.findAll();
        List<Coupon> coupons = couponService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("coupons", coupons);

        return "admin/memberCoupon";
    }

    @PostMapping("/admin/coupons")
    public String memberCouponGrant(@RequestParam("memberId") Long memberId,
                                    @RequestParam("couponId") Long couponId,
                                    @RequestParam("count") int count) {


        Boolean check = memberCouponService.checkAndUpdateCount(memberId, couponId, count);
        if (check) {
            return "redirect:/admin/home";
        }

        Member member = memberService.findById(memberId);
        Coupon coupon = couponService.findById(couponId);
        MemberCoupon memberCoupon = MemberCoupon.createMemberCoupon(member, coupon, count);
        memberCouponService.save(memberCoupon);

        return "redirect:/admin/home";
    }
}
