package kr.sujin.cafereview.service.review;

import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.service.member.MemberCreateService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewDeleteService {
    private final ReviewRepository reviewRepository;
    
    private static final Logger log = LoggerFactory.getLogger(ReviewDeleteService.class);

    public void deleteReview(Long reviewId, String email){
        // 이미 존재하지 않는 리뷰 -> EntityNotFoundException
        Review review = reviewRepository.getReviewById(reviewId).orElseThrow(EntityNotFoundException::new);
        
        if (!email.equals(review.getEmail())){
            // 권한 없음
            log.error("====================================");
            log.error("Review delete request from Unauthorized user");
            log.error("INFO");
            log.error("review id: "+ reviewId);
            log.error("user email: "+ email);
            log.error("====================================");
            throw new AccessDeniedException("삭제 권한 없음");
        }

        // softDelete
        reviewRepository.softDeleteByReviewId(reviewId, false);
    }

    public void deleteReviewByAdmin(Long reviewId, String email){
        // 이미 삭제된 리뷰 -> EntityNotFoundException
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
