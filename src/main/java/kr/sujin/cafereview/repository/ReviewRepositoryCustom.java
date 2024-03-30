package kr.sujin.cafereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;

public interface ReviewRepositoryCustom {
    // Page<Review> getAdminReviewPage(ReviewSearchDto reviewSearchDto, Pageable pageable);

    Page<ReviewReadDto> getReviewWithPaging(Pageable pageable);

    Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
