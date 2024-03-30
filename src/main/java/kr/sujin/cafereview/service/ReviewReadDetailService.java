package kr.sujin.cafereview.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.ReviewImgDto;
import kr.sujin.cafereview.dto.ReviewReadDetailDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewReadDetailService {
    
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    @Transactional(readOnly = true)
    public ReviewReadDetailDto getReviewDetail(Long reviewId){

        // ReviewImg 조회
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for(ReviewImg reviewImg : reviewImgList){
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }

        // Review 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        ReviewReadDetailDto reviewReadDetailDto = ReviewReadDetailDto.of(review);
        reviewReadDetailDto.setReviewImgDtoList(reviewImgDtoList);
        return reviewReadDetailDto;
    }
}
