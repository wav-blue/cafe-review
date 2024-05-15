package kr.sujin.cafereview.bookmark.repository;

import kr.sujin.cafereview.entity.Bookmark;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>,
QuerydslPredicateExecutor<Bookmark>, BookmarkRepositoryCustom {
    List<Bookmark> findByUserEmail(String email);

    Optional<Bookmark> getBookmarkById(Long bookmarkId);

    Optional<Bookmark> findByReviewId(Long reviewId);

    Optional<Bookmark> findByReviewIdAndUserEmail(Long reviewId, String userEmail);
}
