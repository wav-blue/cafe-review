package kr.sujin.cafereview.service.review;

import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewDeleteService {
    private final ReviewRepository reviewRepository;

    public void deleteReview(Long reviewId, String email){
        // 이미 존재하지 않는 리뷰 -> EntityNotFoundException
        Review review = reviewRepository.getReviewById(reviewId).orElseThrow(EntityNotFoundException::new);
        
        System.out.println(email.equals(review.getEmail()));
        if (!email.equals(review.getEmail())){
            // 권한 없음
            throw new AccessDeniedException("삭제 권한 없음");
        }

        // softDelete
        reviewRepository.softDeleteByReviewId(reviewId, false);
    }

    public void deleteReviewByAdmin(Long reviewId, String email){
        // 이미 삭제된 않는 리뷰 -> EntityNotFoundException
        reviewRepository.findByIdAndDeletedDateIsNull(reviewId).orElseThrow(EntityNotFoundException::new);

        // softDelete
        reviewRepository.softDeleteByReviewId(reviewId, true);
    }

    public void updateDeletedStatusReviewByAdmin(Long reviewId, String email){
        // 데이터베이스 내에 존재하지 않는 리뷰 -> EntityNotFoundException
        reviewRepository.getReviewById(reviewId).orElseThrow(EntityNotFoundException::new);

        reviewRepository.updateDeletedStatusToCreatedAndDeletedDateToNull(reviewId);
    }
}
