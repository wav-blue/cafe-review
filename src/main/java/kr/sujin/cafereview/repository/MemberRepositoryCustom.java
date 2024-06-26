package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.dto.member.MemberUpdatePasswordDto;

public interface MemberRepositoryCustom {

    void updateMemberPasswordByEmail(MemberUpdatePasswordDto memberUpdatePasswordDto, String email);
}
