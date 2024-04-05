package kr.sujin.cafereview.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.constant.DeletedStatus;
import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.entity.QReview;
import kr.sujin.cafereview.entity.QReviewImg;
import kr.sujin.cafereview.dto.QReviewReadDto;
import kr.sujin.cafereview.dto.QReviewReadRandomDto;
import kr.sujin.cafereview.dto.ReviewReadAdminDto;
import kr.sujin.cafereview.dto.QReviewReadAdminDto;

import java.time.LocalDateTime;
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
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
        .where(reviewImg.isThumbnail.isTrue())
        .where(review.deletedDate.isNull())
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
        .where(reviewImg.isThumbnail.isTrue())
        .where(searchByKeyword(reviewSearchDto), searchCafeRegion(reviewSearchDto.getRegion()))
        .where(review.deletedDate.isNull())
        .orderBy(review.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

        List<ReviewReadDto> content = results.getResults();
        long total = results.getTotal();
        System.out.println(pageable);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<ReviewReadRandomDto> getReviewByRandom(Integer count){
        QReview review = QReview.review;
        QReviewImg reviewImg = QReviewImg.reviewImg;

        QueryResults<ReviewReadRandomDto> results = queryFactory
        .select(
            new QReviewReadRandomDto(
                review.id,
                review.cafeNm,
                reviewImg.imgUrl
            )
        )
        .from(reviewImg)
        .join(reviewImg.review, review)
        .where(reviewImg.isThumbnail.isTrue())
        .where(review.deletedDate.isNull())
        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
        .limit(count)
        .fetchResults();


        List<ReviewReadRandomDto> content = results.getResults();
        return content;
    }

    @Override
    public void softDeleteByReviewId(Long reviewId, Boolean byAdmin){
        QReview review = QReview.review;

        DeletedStatus deletedStatus = byAdmin? DeletedStatus.DELETED_BY_ADMIN : DeletedStatus.DELETED;

        queryFactory
        .update(review)
        .set(review.deletedDate, LocalDateTime.now())
        .set(review.deletedStatus, deletedStatus)
        .where(review.id.eq(reviewId))
        .execute();
    }

    @Override
    public void updateDeletedStatusToCreatedAndDeletedDateToNull(Long reviewId){
        QReview review = QReview.review;

        queryFactory
        .update(review)
        .setNull(review.deletedDate)
        .set(review.deletedStatus, DeletedStatus.CREATED)
        .where(review.id.eq(reviewId))
        .execute();
    }

    @Override
    public Page<ReviewReadAdminDto> getReviewForAdminWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        QReview review = QReview.review;

        
        QueryResults<ReviewReadAdminDto> results = queryFactory
        .select(
            new QReviewReadAdminDto(
                review.id,
            review.email,
            review.cafeNm,
            review.menuNm,
            review.deletedStatus
        )
        )
        .from(review)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

        List<ReviewReadAdminDto> content = results.getResults();

        long total = results.getTotal();
        System.out.println("total");
        System.out.println(total);
        return new PageImpl<>(content, pageable, total);
    }

}