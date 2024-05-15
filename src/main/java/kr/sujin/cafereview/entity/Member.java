package kr.sujin.cafereview.entity;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.constant.Role;
import kr.sujin.cafereview.member.repository.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy="uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private CafeRegion recommendRegion;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setRecommendRegion(CafeRegion.BUSAN);
        if(memberFormDto.getIsAdmin()){
            member.setRole(Role.ADMIN);
        } else{
            member.setRole(Role.USER);
        }
        member.setPassword(password);
        return member;
    }
}
