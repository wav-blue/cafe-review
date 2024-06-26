package kr.sujin.cafereview.repository;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.sujin.cafereview.dto.member.MemberUpdatePasswordDto;
import kr.sujin.cafereview.entity.QMember;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void updateMemberPasswordByEmail(MemberUpdatePasswordDto memberUpdatePasswordDto, String email){
        QMember member = QMember.member;

        System.out.println("password");
        System.out.println(memberUpdatePasswordDto.getNewEncryptedPassword());

        queryFactory
        .update(member)
        .set(member.password, memberUpdatePasswordDto.getNewEncryptedPassword())
        .where(member.email.eq(email))
        .execute();
    }

}