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
import kr.sujin.cafereview.lib.constant.TagType;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.repository.ReviewTagRepository;
import kr.sujin.cafereview.service.review.ReviewCreateService;
import kr.sujin.cafereview.service.review.ReviewTagCreateService;
import kr.sujin.cafereview.dto.review.ReviewFormDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewTag;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ReviewTagCreateServiceTest {
    @Autowired
    ReviewTagCreateService reviewTagCreateService;

    @Autowired
    ReviewTagRepository reviewTagRepository;

    @Test
    @DisplayName("리뷰 태그 생성 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void saveReviewTag() throws Exception{
        String userEmail = "testEmail1@exam.com";

        List<String> testTagList = new ArrayList<>();
        testTagList.add("DRINK_TASTE");
        // testTagList.add("DESSERT_TASTE");

        Long reviewId = Long.valueOf(22);

        reviewTagCreateService.createReviewTags(testTagList, reviewId);

        // 생성 후 확인
        List<ReviewTag> reviewTagResults = reviewTagRepository.findByReviewId(reviewId);

        if (reviewTagResults == null){
            fail("Review Tag Create Failed");
        }
        assertEquals(reviewTagResults.get(0).getTagType(), "DRINK_TASTE");
    }

}
