package kr.sujin.cafereview.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.sujin.cafereview.bookmark.repository.dto.BookmarkReadDto;



public interface BookmarkRepositoryCustom {
    Page<BookmarkReadDto> getBookmarkReviewWithPaging(String email, Pageable pageable);
}
