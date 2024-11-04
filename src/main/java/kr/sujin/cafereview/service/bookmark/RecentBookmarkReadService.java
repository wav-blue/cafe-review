package kr.sujin.cafereview.service.bookmark;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.sujin.cafereview.repository.RedisRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentBookmarkReadService {
    private final RedisRepository redisRepository;

    public List<Long> readRecentBookmarkReviewIds(){
        String reviewIds = redisRepository.getValues("RecentBookmarkReviews");
        List<Long> castReviewIds = new ArrayList<>();
        if(reviewIds.length()==0){
            return castReviewIds;
        }
        for(String e: reviewIds.split(" ")){
            castReviewIds.add(Long.valueOf(e));
        }
        return castReviewIds;
    }
}
