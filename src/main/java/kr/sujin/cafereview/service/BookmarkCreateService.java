package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.BookmarkCreateDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkCreateService {
    private final BookmarkRepository bookmarkRepository;
    private final ReviewReadService reviewReadService;

    public Long addBookmark(BookmarkCreateDto bookmarkCreateDto, String email){
        
        Long reviewId = bookmarkCreateDto.getReviewId();
        Review review = reviewReadService.getReview(reviewId);

        Optional<Bookmark> bookmark = bookmarkRepository.findByReviewIdAndUserEmail(bookmarkCreateDto.getReviewId(), email);

        System.out.println(bookmark);
        if(bookmark.isPresent()){
            throw new IllegalStateException("이미 북마크된 리뷰입니다.");
        }

        Bookmark newBookmark = Bookmark.createBookmark(bookmarkCreateDto, email, review);
        bookmarkRepository.save(newBookmark);

        return bookmarkCreateDto.getReviewId();
    }

}
