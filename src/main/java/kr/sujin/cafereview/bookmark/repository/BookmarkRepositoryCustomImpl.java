package kr.sujin.cafereview.bookmark.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.sujin.cafereview.bookmark.repository.dto.BookmarkReadDto;
import kr.sujin.cafereview.bookmark.repository.dto.QBookmarkReadDto;
import kr.sujin.cafereview.entity.QBookmark;
import kr.sujin.cafereview.entity.QReview;

public class BookmarkRepositoryCustomImpl implements BookmarkRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public BookmarkRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    @Override
    public Page<BookmarkReadDto> getBookmarkReviewWithPaging(String email, Pageable pageable){
        QBookmark bookmark = QBookmark.bookmark;
        QReview review = QReview.review;

        QueryResults<BookmarkReadDto> results = queryFactory
            .select(
            new QBookmarkReadDto(
                bookmark.id,
                review.id,
                review.cafeNm,
                review.menuNm,
                review.cafeRegion,
                bookmark.firstImgUrl
            )
        ).from(bookmark)
        .join(bookmark.review, review)
        .where(bookmark.userEmail.eq(email))
        .orderBy(bookmark.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchResults();

        List<BookmarkReadDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
