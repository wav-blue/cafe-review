package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.entity.Bookmark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>,
QuerydslPredicateExecutor<Bookmark>, BookmarkRepositoryCustom {
    List<Bookmark> findByUserEmail(String email);

    Bookmark findByReviewId(Long reviewId);
}
