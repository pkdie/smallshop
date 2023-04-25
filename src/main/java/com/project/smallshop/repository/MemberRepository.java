package com.project.smallshop.repository;

import com.project.smallshop.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long MemberId);

    List<Member> findByEmail(String email);

    List<Member> findAll();

}
