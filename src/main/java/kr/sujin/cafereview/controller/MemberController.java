package kr.sujin.cafereview.controller;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.dto.MemberFormDto;
import kr.sujin.cafereview.dto.MemberReadDto;
import kr.sujin.cafereview.dto.MemberUpdatePasswordDto;
import kr.sujin.cafereview.service.MemberCreateService;
import kr.sujin.cafereview.service.MemberReadService;
import kr.sujin.cafereview.service.MemberUpdateService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberCreateService memberCreateService;
    private final MemberReadService memberReadService;
    private final MemberUpdateService memberUpdateService;


    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        model.addAttribute("cafeRegion", CafeRegion.values());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        
        if(bindingResult.hasErrors()){ //유효성검사 결과 X
            model.addAttribute("cafeRegion", CafeRegion.values());
            return "member/memberForm";
        }

        try {
            memberCreateService.saveMember(memberFormDto);
        } catch (IllegalStateException e){ 
            model.addAttribute("cafeRegion", CafeRegion.values());
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/my")
    public String getMyPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        MemberReadDto memberReadDto = memberReadService.getMemberByEmail(email);
        
        model.addAttribute("memberReadDto", memberReadDto);

        return "/member/myPage";
    }

    @GetMapping(value = "/password")
    public String getUdateMemberPasswordForm(Model model) {
        model.addAttribute("memberUpdatePasswordDto", new MemberUpdatePasswordDto());
        return "member/passwordForm";
    }
    

    @PostMapping(value = "/password")
    public String postUdateMemberPasswordForm(
        @Valid MemberUpdatePasswordDto memberUpdatePasswordDto, BindingResult bindingResult, Model model, Principal principal) {
            if(bindingResult.hasErrors()){
                return "member/passwordForm";
            } 
                    
            try{
                String email = principal.getName();
                memberUpdateService.updateMemberPassword(memberUpdatePasswordDto, email);
            }catch(Exception e){
                model.addAttribute("errorMessage", "비밀번호 수정 중 에러가 발생했습니다.");
                return "member/passwordForm";
            }
            return "redirect:/";
    }
}