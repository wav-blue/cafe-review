package kr.sujin.cafereview.service;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.repository.BookmarkRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkDeleteService {
    private final BookmarkRepository bookmarkRepository;

    public void deleteBookmark(Long bookmarkId){
        Bookmark bookmark = bookmarkRepository.getBookmarkById(bookmarkId).orElseThrow(EntityNotFoundException::new);
        bookmarkRepository.delete(bookmark);
    }
}
