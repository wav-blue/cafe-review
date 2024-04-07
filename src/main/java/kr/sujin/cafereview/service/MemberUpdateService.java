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
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public Member updateMemberPassword(MemberUpdatePasswordDto memberUpdatePasswordDto, String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        String newEncryptedPassword = passwordEncoder.encode(memberUpdatePasswordDto.getNewPassword());
        memberUpdatePasswordDto.setNewEncryptedPassword(newEncryptedPassword);
        
        memberRepository.updateMemberPasswordByEmail(memberUpdatePasswordDto, email);

        return member;
    }

}