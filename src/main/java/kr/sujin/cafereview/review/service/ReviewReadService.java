package kr.sujin.cafereview.review.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.member.repository.dto.MemberReadDto;
import kr.sujin.cafereview.member.service.MemberReadService;
import kr.sujin.cafereview.review.repository.ReviewRepository;
import kr.sujin.cafereview.review.repository.dto.ReviewReadAdminDto;
import kr.sujin.cafereview.review.repository.dto.ReviewReadDto;
import kr.sujin.cafereview.review.repository.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.review.repository.dto.ReviewSearchDto;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewReadService {

    private final ReviewRepository reviewRepository;

    private final MemberReadService memberReadService;
    
    public Review getReview(Long reviewId){
        Review review = reviewRepository.getReviewById(reviewId)
        .orElseThrow(EntityNotFoundException::new);
        return review;
    }

    public Page<ReviewReadDto> getReviewWithPaging(Pageable pageable){
        return reviewRepository.getReviewWithPaging(pageable);
    }

    public Page<ReviewReadDto> getReviewByRegionWithPaging(Pageable pageable, String email){
        
        MemberReadDto memberReadDto = memberReadService.getMemberByEmail(email);

        CafeRegion cafeRegion = memberReadDto.getRecommendRegion();

        return reviewRepository.getReviewByRegionWithPaging(cafeRegion, pageable);
    }

    public Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        return reviewRepository.getReviewWithPagingBySearch(reviewSearchDto, pageable);
    }

    public List<ReviewReadRandomDto> getReviewByRandom(Integer count){
        return reviewRepository.getReviewByRandom(count);
    }

    public Page<ReviewReadAdminDto> getReviewForAdminWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        return reviewRepository.getReviewForAdminWithPagingBySearch(reviewSearchDto, pageable);
    }
}
