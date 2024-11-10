package kr.sujin.cafereview.service.bookmark;

import kr.sujin.cafereview.repository.BookmarkRepository;
import kr.sujin.cafereview.dto.bookmark.BookmarkCreateDto;
import kr.sujin.cafereview.entity.Bookmark;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.lib.exception.AlreadyExistBookmarkException;
import kr.sujin.cafereview.service.review.ReviewReadService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkCreateService {
    
    private final BookmarkRepository bookmarkRepository;
    private final ReviewReadService reviewReadService;
    private final RecentBookmarkSetService recentBookmarkSetService;

    private static final Logger log = LoggerFactory.getLogger(BookmarkCreateService.class);

    public Long addBookmark(BookmarkCreateDto bookmarkCreateDto, String email){
        
        Long reviewId = bookmarkCreateDto.getReviewId();
        Review review = reviewReadService.getReview(reviewId);

        Optional<Bookmark> bookmark = bookmarkRepository.findByReviewIdAndUserEmail(bookmarkCreateDto.getReviewId(), email);

        if(bookmark.isPresent()){
            log.debug("Already bookmarked");
            throw new AlreadyExistBookmarkException("이미 북마크된 리뷰입니다.");
        }

        // 최근 북마크된 리뷰 리스트에 추가(Redis)
        recentBookmarkSetService.setRecentBookmarkReviews(bookmarkCreateDto.getReviewId());

        Bookmark newBookmark = Bookmark.createBookmark(bookmarkCreateDto, email, review);
        bookmarkRepository.save(newBookmark);

        return bookmarkCreateDto.getReviewId();
    }

}
