package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.BookmarkReadDto;
import kr.sujin.cafereview.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReadService {
    private final BookmarkRepository bookmarkRepository;

    public Page<BookmarkReadDto> readBookmarkWithPaging(String email, Pageable pageable){
        Page<BookmarkReadDto> bookmarkReadDtos = bookmarkRepository.getBookmarkReviewWithPaging(email, pageable);
        return bookmarkReadDtos;
    }

}
