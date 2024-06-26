package kr.sujin.cafereview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.bookmark.BookmarkReadDto;
import kr.sujin.cafereview.repository.BookmarkRepository;
import kr.sujin.cafereview.service.bookmark.BookmarkReadService;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class BookmarkReadServiceTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookmarkReadService bookmarkReadService;

    @Test
    @DisplayName("북마크 조회 테스트")
    @WithMockUser(username = "testEmail2@exam.com", roles = "USER")
    void readBookmark() throws Exception{
        String email = "testEmail2@exam.com";
        //Pageable
        Pageable pageable = PageRequest.of(0, 6);

        Page<BookmarkReadDto> bookmarkReadDto 
            = bookmarkReadService.readBookmarkWithPaging(email, pageable);

        List<BookmarkReadDto> bookmarkContents = bookmarkReadDto.getContent();

        assertEquals(27, bookmarkContents.get(0).getReviewId());
        assertEquals(26, bookmarkContents.get(1).getReviewId());
        assertEquals(25, bookmarkContents.get(2).getReviewId());
        
    }

}