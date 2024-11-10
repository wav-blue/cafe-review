package kr.sujin.cafereview.service.review;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.service.bookmark.RecentBookmarkReadService;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.dto.review.ReviewReadRandomDto;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewReadPopularService {

    private final ReviewRepository reviewRepository;

    private final RecentBookmarkReadService recentBookmarkReadService;

    public List<ReviewReadRandomDto> getRecentBookmarkReviews(int count){
        Map<Long, Integer> recentBookmarkReviews = recentBookmarkReadService.readRecentBookmarkReviewIdsWithMap();

        int arrSize = recentBookmarkReviews.size();
        if(arrSize < count){
            // 예외 상황 시 완전히 랜덤으로 리뷰 선정
            return reviewRepository.getReviewByRandom(count);
        }

        List<Long> popularIds = new ArrayList<>();

        List<Map.Entry<Long, Integer>> entryList = new LinkedList<>(recentBookmarkReviews.entrySet());

        entryList.sort(Map.Entry.comparingByValue());

        for(int i = 1 ; i <= 3 ; i++){
            popularIds.add(entryList.get(arrSize-i).getKey());
        }

        return reviewRepository.getReviewByRandomIds(popularIds);
    }
}
