package kr.sujin.cafereview.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.entity.Member;

@Getter
@Setter
public class MemberReadWriterDto {

    private String email;

    private String name;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberReadWriterDto of(Member member) {return modelMapper.map(member, MemberReadWriterDto.class);}
}