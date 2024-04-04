package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.dto.BookmarkCreateDto;
import kr.sujin.cafereview.dto.ReviewFormDto;
import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.ReviewRepository;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ReviewServiceTest {
    @Autowired
    ReviewCreateService reviewCreateService;

    @Autowired
    ReviewDeleteService reviewDeleteService;
    
    @Autowired
    ReviewReadService reviewReadService;

    @Autowired
    ReviewReadDetailService reviewReadDetailService;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 생성 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void saveReview() throws Exception{
        String userEmail = "testEmail1@exam.com";

        String testCafeNm = "리뷰 생성 테스트용 카페이름";

        ReviewFormDto reviewFormDto = new ReviewFormDto();
        reviewFormDto.setCafeNm(testCafeNm);
        reviewFormDto.setMenuNm("메뉴이름");
        reviewFormDto.setCafeRegion(CafeRegion.ETC);
        reviewFormDto.setRating(5);

        String name = "test_img.jpg";
        String originalFilename = "test.jpg";
        String contentType = "jpg";
        byte[] content = null;

        // 이미지 설정
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, originalFilename,contentType, content);
        
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(mockMultipartFile);
        
        reviewCreateService.createReview(reviewFormDto, multipartFiles, userEmail);

        // 생성 후 확인
        Review review = reviewRepository.findByCafeNm(testCafeNm);

        if (review == null){
            fail("Review Create Failed");
        }
        assertEquals(testCafeNm, review.getCafeNm());
    }

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


    @Test
    @DisplayName("리뷰 검색 조회")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void readReviewWithPageBySearch() throws Exception{

        String testSearchKeyword = "북마크";

        ReviewSearchDto reviewSearchDto = new ReviewSearchDto();
        reviewSearchDto.setRegion(null);
        reviewSearchDto.setSearchBy("cafeNm");
        reviewSearchDto.setSearchKeyword(testSearchKeyword);

        Pageable pageable = PageRequest.of(0, 3);

        Page<ReviewReadDto> reviews =reviewReadService.getReviewWithPagingBySearch(reviewSearchDto, pageable);
        
        List<ReviewReadDto> reviewsContents = reviews.getContent();
        
        for(ReviewReadDto e : reviewsContents){
            if (e.getCafeNm().contains(testSearchKeyword)){
                System.out.println(e.getCafeNm());
            } else{
                fail("Test failed"+ e.getCafeNm() + " " + testSearchKeyword);
            }
        }

    }
}
