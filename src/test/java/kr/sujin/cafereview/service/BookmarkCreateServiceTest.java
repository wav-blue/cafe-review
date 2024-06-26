package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.bookmark.BookmarkCreateDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.repository.BookmarkRepository;
import kr.sujin.cafereview.service.bookmark.BookmarkCreateService;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class BookmarkCreateServiceTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookmarkCreateService bookmarkCreateService;

    @Test
    @DisplayName("북마크 추가 테스트")
    @WithMockUser(username = "testEmail1@exam.com", roles = "ADMIN")
    void saveBookmark() throws Exception{
        String userEmail = "testEmail1@exam.com";

        BookmarkCreateDto bookmarkCreateDto = new BookmarkCreateDto();
        bookmarkCreateDto.setReviewId(Long.valueOf(22));
        bookmarkCreateDto.setFirstImgUrl("/img/uploads/test_img.jpg");

        Long reviewId = bookmarkCreateService.addBookmark(bookmarkCreateDto, userEmail);
        
        // ReviewId, 저장한 유저 Email로 조회
        Bookmark bookmark = bookmarkRepository.findByReviewIdAndUserEmail(reviewId, userEmail)
        .orElseThrow(EntityNotFoundException::new);

        // 저장된 reviewId 일치 확인
        assertEquals(bookmarkCreateDto.getReviewId(), bookmark.getReview().getId());
        assertEquals(bookmarkCreateDto.getFirstImgUrl(), bookmark.getFirstImgUrl());
    }
    
    @Test
    @DisplayName("북마크 추가 테스트: 중복 저장 불가")
    @WithMockUser(username = "testEmail2@exam.com", roles = "USER")
    void saveBookmarkForException() throws Exception{
        String userEmail = "testEmail2@exam.com";

        BookmarkCreateDto bookmarkCreateDto = new BookmarkCreateDto();
        bookmarkCreateDto.setReviewId(Long.valueOf(25));
        bookmarkCreateDto.setFirstImgUrl("/img/uploads/test_img.jpg");

        assertThrows(IllegalStateException.class, () -> {
            bookmarkCreateService.addBookmark(bookmarkCreateDto, userEmail);
        });
    }

}