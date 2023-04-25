package com.project.smallshop.Service;

import com.project.smallshop.domain.member.Member;
import com.project.smallshop.domain.member.MemberType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    Member findById(Long memberId);

    List<Member> findByEmail(String email);

    Member login(String email, String pwd);

    List<Member> findAll();

    void updateTypeToAdmin(Long memberId);

    void updateTypeToUser(Long memberId);
}
