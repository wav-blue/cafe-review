package kr.sujin.cafereview.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.lib.constant.CafeRegion;
import kr.sujin.cafereview.lib.constant.Role;
import kr.sujin.cafereview.entity.Member;

@Getter
@Setter
public class MemberReadDto {

    private String name;

    private String email;

    private String address;

    private Role role;

    private CafeRegion recommendRegion;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberReadDto of(Member member) {return modelMapper.map(member, MemberReadDto.class);}
}