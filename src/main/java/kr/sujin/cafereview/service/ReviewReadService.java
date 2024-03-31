package kr.sujin.cafereview.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReadService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<ReviewReadDto> getReviewWithPaging(Pageable pageable){
        return reviewRepository.getReviewWithPaging(pageable);
    }

    public Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        return reviewRepository.getReviewWithPagingBySearch(reviewSearchDto, pageable);
    }

    public List<ReviewReadRandomDto> getReviewByRandom(Integer count){
        return reviewRepository.getReviewByRandom(count);
    }

    public Review getReview(Long reviewId){
        return reviewRepository.getReviewById(reviewId);
    }
}
