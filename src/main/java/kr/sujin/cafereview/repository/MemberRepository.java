package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.dto.MemberReadWriterDto;
import kr.sujin.cafereview.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository  extends JpaRepository<Member, String>,
QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);

    MemberReadWriterDto findWriterByEmail(String email);
}
