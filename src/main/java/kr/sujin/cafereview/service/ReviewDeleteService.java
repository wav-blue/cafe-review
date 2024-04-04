package kr.sujin.cafereview.service;

import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewDeleteService {
    private final ReviewRepository reviewRepository;

    public void deleteReview(Long reviewId){
        // 이미 존재하지 않는 리뷰 -> EntityNotFoundException
        reviewRepository.getReviewById(reviewId).orElseThrow(EntityNotFoundException::new);;

        // softDelete
        reviewRepository.deleteByReviewId(reviewId);
    }
}
