package kr.sujin.cafereview.member.repository.dto;

import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.entity.Member;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MemberUpdatePasswordDto {

    @NotEmpty(message = "기존 비밀번호가 입력되지 않았습니다.")
    private String password;

    @NotEmpty(message = "변경할 비밀번호가 입력되지 않았습니다.")
    private String newPassword;

    private String newEncryptedPassword;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberUpdatePasswordDto of(Member member) {return modelMapper.map(member, MemberUpdatePasswordDto.class);}
}
