package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.lib.constant.CafeRegion;
import kr.sujin.cafereview.repository.MemberRepository;
import kr.sujin.cafereview.service.member.MemberCreateService;
import kr.sujin.cafereview.dto.member.MemberFormDto;
import kr.sujin.cafereview.entity.Member;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class MemberCreateServiceTest {
    @Autowired
    MemberCreateService memberCreateService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원(USER) 생성 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void saveMember() throws Exception{
        MemberFormDto memberFormDto = new MemberFormDto();

        String testName = "테스트용 계정";
        String testEmail = "testEmail9999@test.net";
        String testPassword = "testEmail";

        memberFormDto.setName(testName);
        memberFormDto.setEmail(testEmail);
        memberFormDto.setPassword(testPassword);
        memberFormDto.setAddress("테스트 주소");
        memberFormDto.setIsAdmin(false);
        memberFormDto.setRecommendRegion(null);

        Optional<Member> alreadyMember =  memberRepository.findByEmail(testEmail);
        
        if(alreadyMember.isPresent()){
            fail("테스트하려는 이메일로 등록된 유저가 존재함");
        }

        Member member = memberCreateService.saveMember(memberFormDto);

        Optional<Member> savedMember =  memberRepository.findByEmail(testEmail);

        if(savedMember.isEmpty()){
            fail("테스트로 저장된 내역 조회 실패");
        } 

        if (savedMember.isPresent()) {
            assertEquals(Optional.of(member), savedMember);
        }

    }

    
    @Test
    @DisplayName("회원(ADMIN) 생성 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void saveMemberAdmin() throws Exception{
        MemberFormDto memberFormDto = new MemberFormDto();

        String testName = "테스트용 계정";
        String testEmail = "testEmail9999@test.net";
        String testPassword = "testEmail";

        memberFormDto.setName(testName);
        memberFormDto.setEmail(testEmail);
        memberFormDto.setPassword(testPassword);
        memberFormDto.setAddress("테스트 주소");
        memberFormDto.setIsAdmin(true);
        memberFormDto.setRecommendRegion(CafeRegion.BUSAN);

        Optional<Member> alreadyMember =  memberRepository.findByEmail(testEmail);
        
        if(alreadyMember.isPresent()){
            fail("테스트하려는 이메일로 등록된 유저가 존재함");
        }

        Member member = memberCreateService.saveMember(memberFormDto);

        Optional<Member> savedMember =  memberRepository.findByEmail(testEmail);

        if(savedMember.isEmpty()){
            fail("테스트로 저장된 내역 조회 실패");
        } 

        if (savedMember.isPresent()) {
            assertEquals(Optional.of(member), savedMember);
        }

    }

}
