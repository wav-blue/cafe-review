package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.MemberUpdatePasswordDto;
import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberUpdatePasswordService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public Member updateMemberPassword(MemberUpdatePasswordDto memberUpdatePasswordDto){
        String email = memberUpdatePasswordDto.getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        String newPassword = passwordEncoder.encode(memberUpdatePasswordDto.getPassword());
        memberUpdatePasswordDto.setPassword(newPassword);
        
        memberRepository.updateMemberPasswordByEmail(memberUpdatePasswordDto);

        return member;
    }

}