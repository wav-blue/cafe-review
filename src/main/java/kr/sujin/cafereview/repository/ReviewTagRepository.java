package kr.sujin.cafereview.repository;

import kr.sujin.cafereview.entity.ReviewTag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long>{

    List<ReviewTag> findByReviewId(Long reviewId);

}
