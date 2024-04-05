package kr.sujin.cafereview.controller;

import kr.sujin.cafereview.dto.MemberFormDto;
import kr.sujin.cafereview.dto.MemberReadDto;
import kr.sujin.cafereview.service.MemberCreateService;
import kr.sujin.cafereview.service.MemberReadService;
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

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberCreateService memberCreateService;
    private final MemberReadService memberReadService;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){ //유효성검사 결과 X
            return "member/memberForm";
        }

        try {
            memberCreateService.saveMember(memberFormDto);
        } catch (IllegalStateException e){ 
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
    
}