package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.entity.Review;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>,
QuerydslPredicateExecutor<Review>, ReviewRepositoryCustom{
    Optional<Review> getReviewById(Long id);

    Review findByCafeNm(String cafeNm);
}
