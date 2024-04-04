package kr.sujin.cafereview.service;

import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.message.AuthException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewDeleteService {
    private final ReviewRepository reviewRepository;

    public void deleteReview(Long reviewId, String email){
        // 이미 존재하지 않는 리뷰 -> EntityNotFoundException
        Review review = reviewRepository.getReviewById(reviewId).orElseThrow(EntityNotFoundException::new);
        if (review.getEmail() != email){
            // 권한 없음
            throw new AccessDeniedException("삭제 권한 없음");
        }
        

        // softDelete
        reviewRepository.deleteByReviewId(reviewId);
    }
}
