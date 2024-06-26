package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.sujin.cafereview.lib.constant.CafeRegion;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.service.review.ReviewCreateService;
import kr.sujin.cafereview.dto.review.ReviewFormDto;
import kr.sujin.cafereview.entity.Review;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ReviewServiceCreateTest {
    @Autowired
    ReviewCreateService reviewCreateService;

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

}
