package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.dto.MemberUpdatePasswordDto;

public interface MemberRepositoryCustom {

    void updateMemberPasswordByEmail(MemberUpdatePasswordDto memberUpdatePasswordDto, String email);
}
