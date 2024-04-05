package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ReviewServiceDeleteTest {
    @Autowired
    ReviewDeleteService reviewDeleteService;
    
    @Autowired
    ReviewRepository reviewRepository;

    // 리뷰 삭제 테스트
    @Test
    @DisplayName("리뷰 삭제 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void deleteReview() throws Exception{
        String email = "testEmail1@exam.com";

        Long testReviewId = Long.valueOf(22);
        
        // 테스트하려는 리뷰 확인
        Optional<Review> review = reviewRepository.findByIdAndDeletedDateIsNull(testReviewId);
        if(review.isEmpty()){
            fail("Review does not exist");
        }

        // 리뷰 삭제
        reviewDeleteService.deleteReview(testReviewId, email);

        // 삭제 여부 확인
        review = reviewRepository.findByIdAndDeletedDateIsNull(testReviewId);

        if(review.isPresent()){
            fail("Review Delete Failed");
        }
    }

    // 리뷰 삭제 테스트: 권한 없음
    @Test
    @DisplayName("리뷰 삭제 테스트: 권한 없음")
    @WithMockUser(username = "testEmail2@exam.com", roles = "ADMIN")
    void deleteReviewWithException() throws Exception{
        String email = "testEmail2@exam.com";
        
        Long testReviewId = Long.valueOf(22);
        
        // 테스트하려는 리뷰 확인
        Optional<Review> review = reviewRepository.findByIdAndDeletedDateIsNull(testReviewId);
        if(review.isEmpty()){
            fail("Review does not exist");
        }

        // 리뷰 삭제
        assertThrows(AccessDeniedException.class, () -> {
            reviewDeleteService.deleteReview(testReviewId, email);
        });
    }


}
