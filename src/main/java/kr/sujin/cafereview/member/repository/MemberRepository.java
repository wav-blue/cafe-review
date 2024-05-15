package kr.sujin.cafereview.member.repository;

import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.member.repository.dto.MemberReadWriterDto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository  extends JpaRepository<Member, String>,
QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);

    MemberReadWriterDto findWriterByEmail(String email);
}
