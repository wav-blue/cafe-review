package kr.sujin.cafereview.service.bookmark;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.bookmark.BookmarkReadDto;
import kr.sujin.cafereview.repository.BookmarkRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkReadService {
    private final BookmarkRepository bookmarkRepository;

    public Page<BookmarkReadDto> readBookmarkWithPaging(String email, Pageable pageable){
        Page<BookmarkReadDto> bookmarkReadDtos = bookmarkRepository.getBookmarkReviewWithPaging(email, pageable);
        return bookmarkReadDtos;
    }

}
