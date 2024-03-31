package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.MemberReadDto;
import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReadService{

    private final MemberRepository memberRepository;

    public MemberReadDto getMemberByEmail(String email){
        Member member = memberRepository.findByEmail(email);

        MemberReadDto memberReadDto = MemberReadDto.of(member);
        return memberReadDto;
    }


}