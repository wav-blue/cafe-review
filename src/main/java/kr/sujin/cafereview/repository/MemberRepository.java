package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.dto.MemberReadWriterDto;
import kr.sujin.cafereview.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByEmail(String email);

    MemberReadWriterDto findWriterByEmail(String email);
}
