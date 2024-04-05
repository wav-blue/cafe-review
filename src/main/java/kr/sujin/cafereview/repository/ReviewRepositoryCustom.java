package kr.sujin.cafereview.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.sujin.cafereview.dto.ReviewReadAdminDto;
import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;

public interface ReviewRepositoryCustom {
    Page<ReviewReadDto> getReviewWithPaging(Pageable pageable);

    Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable);

    List<ReviewReadRandomDto> getReviewByRandom(Integer number);
    
    void softDeleteByReviewId(Long reviewId, Boolean byAdmin);

    void updateDeletedStatusToCreatedAndDeletedDateToNull(Long reviewId);

    Page<ReviewReadAdminDto> getReviewForAdminWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
