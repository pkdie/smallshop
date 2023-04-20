package com.project.smallshop.controller;

import com.project.smallshop.Service.CartService;
import com.project.smallshop.Service.MemberService;
import com.project.smallshop.domain.Cart;
import com.project.smallshop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping("member/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("member/login")
    public String signUp(@RequestParam("email") String email, @RequestParam("password") String pwd, HttpServletRequest request, Model model) {

        try {
            Member member = memberService.login(email, pwd);
            HttpSession session = request.getSession();
            session.setAttribute("member", member);
        } catch (Exception e) {
            model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
            return "member/login";
        }

        return "redirect:/";
    }

    @GetMapping("member/logout")
    private String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("member/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping("member/new")
    public String join(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "member/createMemberForm";
        }

        Member member = Member.createMember(form.getEmail(), form.getPwd(), form.getName(), form.getPhone(), form.getAddress());
        Cart cart = Cart.createCart(member);

        memberService.join(member);
        cartService.save(cart);
        return "redirect:/member/login";

    }
}
