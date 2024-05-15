package kr.sujin.cafereview.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.sujin.cafereview.entity.ReviewImg;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);

}
