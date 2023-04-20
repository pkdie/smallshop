package com.project.smallshop.Service;

import com.project.smallshop.domain.member.Member;
import com.project.smallshop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceImplTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입테스트() {
        //given
        Member member = new Member();
        member.setName("황성수");

        //when
        Long savedId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberService.findById(savedId));
        Assertions.assertThat(memberService.findAll().size()).isEqualTo(1);
    }

    @Test
    public void 회원가입중복테스트() {
        //given
        Member member1 = new Member();
        member1.setEmail("황성수");
        Member member2 = new Member();
        member2.setEmail("황성수");

        //when
        memberService.join(member1);

        //then
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    }

    @Test
    public void 회원목록테스트() {
        //given

        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        //when

        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        List<Member> members = memberService.findAll();

        //then

        Assertions.assertThat(members.size()).isEqualTo(3);
    }
}