package kr.sujin.cafereview.service.member;

import kr.sujin.cafereview.dto.member.MemberReadDto;
import kr.sujin.cafereview.dto.member.MemberReadWriterDto;
import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReadService{

    private final MemberRepository memberRepository;

    public MemberReadDto getMemberByEmail(String email){
        Member member = memberRepository.findByEmail(email)
        .orElseThrow(EntityNotFoundException::new);;

        MemberReadDto memberReadDto = MemberReadDto.of(member);
        return memberReadDto;
    }

    public MemberReadWriterDto getMemberWriterByEmail(String email){
        // Review 작성자 조회
        Member member = memberRepository.findByEmail(email)
        .orElseThrow(EntityNotFoundException::new);

        MemberReadWriterDto memberReadWriterDto = MemberReadWriterDto.of(member);

        return memberReadWriterDto;
    }

    
}