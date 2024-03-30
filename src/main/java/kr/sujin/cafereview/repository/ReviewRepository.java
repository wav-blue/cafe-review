package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.entity.Review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>,
QuerydslPredicateExecutor<Review>, ReviewRepositoryCustom{
    Review findOneById(Long id);

    Review findByCafeNm(String cafeNm);
}
