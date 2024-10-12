package kr.sujin.cafereview.service.member;

import kr.sujin.cafereview.dto.member.MemberFormDto;
import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCreateService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(MemberCreateService.class);

    public Member saveMember(MemberFormDto memberFormDto){
        Member member = Member.createMember(memberFormDto, passwordEncoder);

        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()){
            log.debug("Account that already exists");
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}