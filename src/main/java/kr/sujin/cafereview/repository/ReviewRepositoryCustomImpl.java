package kr.sujin.cafereview.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.dto.QReviewReadDto;
import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.entity.QReview;
import kr.sujin.cafereview.entity.QReviewImg;
import kr.sujin.cafereview.entity.Review;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression reviewNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null: QReview.review.cafeNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<ReviewReadDto> getReviewWithPaging(Pageable pageable){
        QReview review = QReview.review;
        QReviewImg reviewImg = QReviewImg.reviewImg;

        QueryResults<ReviewReadDto> results = queryFactory
            .select(
            new QReviewReadDto(
                review.id,
                review.cafeNm,
                review.menuNm,
                review.rating,
                review.reviewDetail,
                review.cafeRegion,
                reviewImg.imgUrl
            )
        ).from(reviewImg)
        .join(reviewImg.review, review)
        .where(reviewImg.repimgYn.eq("Y"))
        .orderBy(review.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

        List<ReviewReadDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    // 리뷰 특정 키워드로 검색
    private BooleanExpression searchByKeyword(ReviewSearchDto reviewSearchDto){
        String searchBy = reviewSearchDto.getSearchBy();
        System.out.println("searchBy : ");
        System.out.println(searchBy);
        String searchKeyword = reviewSearchDto.getSearchKeyword();

        if(StringUtils.equals("cafeNm", searchBy)){
            return QReview.review.cafeNm.like("%" + searchKeyword + "%");
        } else if(StringUtils.equals("menuNm", searchBy)){
            return QReview.review.menuNm.like("%" + searchKeyword + "%");
        }

        return null;
    }

    private BooleanExpression searchCafeRegion(CafeRegion searchCafeRegion){
        return searchCafeRegion == null ? null : QReview.review.cafeRegion.eq(searchCafeRegion);
    }

    @Override
    public Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        QReview review = QReview.review;
        QReviewImg reviewImg = QReviewImg.reviewImg;

        QueryResults<ReviewReadDto> results = queryFactory
        .select(
            new QReviewReadDto(
                review.id,
                review.cafeNm,
                review.menuNm,
                review.rating,
                review.reviewDetail,
                review.cafeRegion,
                reviewImg.imgUrl
            )
        ).from(reviewImg)
        .join(reviewImg.review, review)
        .where(reviewImg.repimgYn.eq("Y"))
        .where(searchByKeyword(reviewSearchDto), searchCafeRegion(reviewSearchDto.getRegion()))
        .orderBy(review.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

        List<ReviewReadDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

}
