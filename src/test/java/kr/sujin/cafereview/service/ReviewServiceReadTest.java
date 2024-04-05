package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.repository.ReviewRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ReviewServiceReadTest {

    @Autowired
    ReviewReadService reviewReadService;

    @Autowired
    ReviewReadDetailService reviewReadDetailService;

    @Autowired
    ReviewRepository reviewRepository;

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
