package kr.sujin.cafereview.service;

import lombok.RequiredArgsConstructor;

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
        try{
            Bookmark bookmark = bookmarkRepository.getBookmarkById(bookmarkId);
            bookmarkRepository.delete(bookmark);
        } catch(Exception e){
            // 삭제 중 문제 발생
        }
        ;
    }
}
