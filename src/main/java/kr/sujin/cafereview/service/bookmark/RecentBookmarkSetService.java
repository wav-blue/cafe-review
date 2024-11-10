package kr.sujin.cafereview.service.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import kr.sujin.cafereview.repository.RedisRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecentBookmarkSetService {
    private final RedisRepository redisRepository;
    private final RecentBookmarkReadService recentBookmarkReadService;

    public void setRecentBookmarkReviews(Long reviewId){
        List<Long> reviewIdList = recentBookmarkReadService.readRecentBookmarkReviewIds();
        
        if(reviewIdList.size() < 20){
            reviewIdList.add(reviewId);
        } else{
            reviewIdList.remove(0);
            reviewIdList.add(reviewId);
        }
        redisRepository.setValues("RecentBookmarkReviews", parseListToString(reviewIdList));
    }

    private String parseListToString(List<Long> reviewIdList){
        if(reviewIdList.isEmpty()){
            return "";
        }
        String value = reviewIdList.get(0).toString();
        for(int i = 1 ; i < reviewIdList.size() ; i++){
            value = value + " " + reviewIdList.get(i).toString();
        }
        return value;
    }

}
