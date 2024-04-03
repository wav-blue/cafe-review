package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.BookmarkCreateDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.repository.BookmarkRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class BookmarkServiceTest {

    @Autowired
    ReviewReadService reviewReadService;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookmarkCreateService bookmarkCreateService;

    @Test
    @DisplayName("북마크 추가 테스트")
    @WithMockUser(username = "exam_admin@test.com", roles = "ADMIN")
    void saveBookmark() throws Exception{
        String userEmail = "exam_admin@test.com";

        BookmarkCreateDto bookmarkCreateDto = new BookmarkCreateDto();
        bookmarkCreateDto.setReviewId(Long.valueOf(19));
        bookmarkCreateDto.setFirstImgUrl("/img/uploads/83973162-0658-4bcd-97fc-c21b48ba0a8b.jpg");

        Long reviewId = bookmarkCreateService.addBookmark(bookmarkCreateDto, userEmail);
        
        // ReviewId, 저장한 유저 Email로 조회
        Bookmark bookmark = bookmarkRepository.findByReviewIdAndUserEmail(reviewId, userEmail)
        .orElseThrow(EntityNotFoundException::new);

        // reviewId 일치 확인
        assertEquals(bookmarkCreateDto.getReviewId(), bookmark.getReview().getId());
        assertEquals(bookmarkCreateDto.getFirstImgUrl(), bookmark.getFirstImgUrl());
    }
    
}