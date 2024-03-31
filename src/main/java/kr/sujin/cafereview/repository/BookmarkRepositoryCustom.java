package kr.sujin.cafereview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.sujin.cafereview.dto.BookmarkReadDto;

public interface BookmarkRepositoryCustom {
    Page<BookmarkReadDto> getBookmarkReviewWithPaging(String email, Pageable pageable);
}
