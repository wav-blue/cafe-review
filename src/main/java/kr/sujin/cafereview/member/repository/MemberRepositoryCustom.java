package kr.sujin.cafereview.member.repository;

import kr.sujin.cafereview.member.repository.dto.MemberUpdatePasswordDto;

public interface MemberRepositoryCustom {

    void updateMemberPasswordByEmail(MemberUpdatePasswordDto memberUpdatePasswordDto, String email);
}
