package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    Review findOneById(Long id);
}
