package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.BookmarkCreateDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkCreateService {
    private final BookmarkRepository bookmarkRepository;

    public Long addBookmark(BookmarkCreateDto bookmarkCreateDto, String email, Review review){
        
        Bookmark bookmark = bookmarkRepository.findByReviewId(bookmarkCreateDto.getReviewId());
        System.out.println(bookmark);
        if(bookmark == null){
            Bookmark newBookmark = Bookmark.createBookmark(bookmarkCreateDto, email, review);
            System.out.println(newBookmark);
            bookmarkRepository.save(newBookmark);
        }

        return bookmarkCreateDto.getReviewId();
    }

}
