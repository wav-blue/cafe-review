package kr.sujin.cafereview.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.review.repository.dto.ReviewReadAdminDto;
import kr.sujin.cafereview.review.repository.dto.ReviewReadDto;
import kr.sujin.cafereview.review.repository.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.review.repository.dto.ReviewSearchDto;

public interface ReviewRepositoryCustom {
    Page<ReviewReadDto> getReviewWithPaging(Pageable pageable);

    Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable);

    List<ReviewReadRandomDto> getReviewByRandom(Integer number);
    
    void softDeleteByReviewId(Long reviewId, Boolean byAdmin);

    void updateDeletedStatusToCreatedAndDeletedDateToNull(Long reviewId);

    Page<ReviewReadDto> getReviewByRegionWithPaging(CafeRegion cafeRegion, Pageable pageable);

    Page<ReviewReadAdminDto> getReviewForAdminWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
